package backend.academy.scrapper.service;

import backend.academy.common.dto.chat.response.ChatResponse;
import backend.academy.common.dto.enums.ResponseType;
import backend.academy.common.dto.request.AddLinkRequest;
import backend.academy.common.dto.request.RemoveLinkRequest;
import backend.academy.common.dto.response.ApiResponse;
import backend.academy.common.dto.response.LinkResponse;
import backend.academy.common.dto.response.ListLinksResponse;
import backend.academy.scrapper.repository.custom.CustomRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommandService {
    @Autowired
    private CustomRepository customRepository;

    @Autowired
    private SimpMessagingTemplate template;

    public ApiResponse<ChatResponse> registerChat(Long chatId) {
        if (customRepository.chatExists(chatId)) {
            return new ApiResponse<>(
                    ResponseType.CHAT_ALREADY_EXISTS,
                    "Этот чат уже был создан! ID: " + chatId,
                    new ChatResponse(chatId));

        } else {
            customRepository.registerChat(chatId);
            return new ApiResponse<>(
                    ResponseType.SUCCESS, "Чат зарегистрирован! ID: " + chatId, new ChatResponse(chatId));
        }
    }

    public ApiResponse<ChatResponse> deleteChat(Long chatId) {
        if (customRepository.chatExists(chatId)) {
            customRepository.deleteChat(chatId);
            return new ApiResponse<>(ResponseType.SUCCESS, "Чат удален! ID: " + chatId, new ChatResponse(chatId));

        } else {
            return new ApiResponse<>(ResponseType.NOT_FOUND, "Чата не существовало", new ChatResponse(chatId));
        }
    }

    public ApiResponse<ListLinksResponse> getLinks(Long id) {
        ListLinksResponse response = new ListLinksResponse();
        List<LinkResponse> links = customRepository.getLinks(id);
        response.links(links);
        response.size(links.size());
        StringBuilder sb = new StringBuilder("Ваши ссылки:\n");
        for (LinkResponse link : links) {
            sb.append("- ").append(link.getUrl()).append("\n");
        }

        String result = sb.toString();
        return new ApiResponse<>(ResponseType.SUCCESS, result, response);
    }

    public ApiResponse<LinkResponse> addLink(Long id, AddLinkRequest linkRequest) {
        if (customRepository.checkLink(id, linkRequest.link())) {
            LinkResponse linkResponse =
                    new LinkResponse(null, linkRequest.link(), linkRequest.tags(), linkRequest.filters());
            customRepository.updateLink(id, linkResponse);
            linkResponse.setId(id);
            return new ApiResponse<>(
                    ResponseType.UPDATED, "Ссылка успешно обновлена %s".formatted(linkRequest.link()), linkResponse);
        } else {
            LinkResponse linkResponse =
                    new LinkResponse(null, linkRequest.link(), linkRequest.tags(), linkRequest.filters());
            customRepository.addLink(id, linkResponse);
            linkResponse.setId(id);
            return new ApiResponse<>(
                    ResponseType.CREATED, "Ссылка успешно добавлена %s".formatted(linkRequest.link()), linkResponse);
        }

        //        return new LinkResponse(id, linkRequest.link(), linkRequest.tags(), linkRequest.filters());
    }

    public ApiResponse<LinkResponse> deleteLink(Long id, RemoveLinkRequest request) {
        if (!customRepository.checkLink(id, request.getLink())) {
            return new ApiResponse<>(ResponseType.NOT_FOUND, "Ссылка не найдена", null);
        }
        LinkResponse linkResponse = new LinkResponse(request.getLink());
        customRepository.deleteLink(id, linkResponse);
        linkResponse.setId(id);
        return new ApiResponse<>(
                ResponseType.CREATED, "Ссылка успешно удалена %s".formatted(request.link()), linkResponse);
    }
}
