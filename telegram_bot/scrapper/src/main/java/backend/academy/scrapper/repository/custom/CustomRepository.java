package backend.academy.scrapper.repository.custom;

import backend.academy.common.dto.response.LinkResponse;
import java.util.List;

public interface CustomRepository {

    List<LinkResponse> getLinks(Long chatId);

    void addLink(Long chatId, LinkResponse linkResponse);

    void deleteLink(Long chatId, LinkResponse linkResponse);

    void deleteAllLinks(Long chatId);

    void updateLink(Long chatId, LinkResponse linkResponse);

    boolean checkLink(Long chatId, String link);

    boolean chatExists(Long chatId);

    void registerChat(Long chatId);

    void deleteChat(Long chatId);
}
