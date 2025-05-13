package backend.academy.scrapper.controllers;

import backend.academy.common.dto.chat.response.ChatResponse;
import backend.academy.common.dto.enums.ResponseType;
import backend.academy.common.dto.request.AddLinkRequest;
import backend.academy.common.dto.request.RemoveLinkRequest;
import backend.academy.common.dto.response.ApiResponse;
import backend.academy.common.dto.response.LinkResponse;
import backend.academy.common.dto.response.ListLinksResponse;
import backend.academy.scrapper.service.CommandService;
import backend.academy.scrapper.service.UpdateMonitoringService;
import backend.academy.scrapper.storage.UserLinkStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommandController {
    @Autowired
    private UpdateMonitoringService updateMonitoringService;

    @Autowired
    private UserLinkStorage userLinkStorage;

    private final CommandService commandService;

    public CommandController(
            CommandService commandService,
            UserLinkStorage userLinkStorage,
            UpdateMonitoringService updateMonitoringService) {
        this.commandService = commandService;
        this.userLinkStorage = userLinkStorage;
        this.updateMonitoringService = updateMonitoringService;
    }

    // POST /tg-chat/{id}
    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<ApiResponse<ChatResponse>> registerChat(@PathVariable Long id) {
        return ResponseEntity.ok(commandService.registerChat(id));
    }

    // DELETE /tg-chat/{id}
    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<ApiResponse<ChatResponse>> deleteChat(@PathVariable Long id) {

        return ResponseEntity.ok(commandService.deleteChat(id));
    }

    // GET /links
    @GetMapping("/links")
    public ResponseEntity<ApiResponse<ListLinksResponse>> getLinks(@RequestHeader("tg-chat-id") Long tgChatId) {
        return ResponseEntity.ok(commandService.getLinks(tgChatId));
    }

    // POST /links
    @PostMapping("/links")
    public ResponseEntity<ApiResponse<LinkResponse>> addLink(
            @RequestHeader("tg-chat-id") Long tgChatId, @RequestBody AddLinkRequest request) {
        if (!updateMonitoringService.checkLink(request.link())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            ResponseType.NOT_FOUND, "Ссылка недействительна или не поддерживается", null));
        }
        return ResponseEntity.ok(commandService.addLink(tgChatId, request));
    }

    // DELETE /links
    @DeleteMapping("/links")
    public ResponseEntity<ApiResponse<LinkResponse>> deleteLink(
            @RequestHeader("tg-chat-id") Long tgChatId, @RequestBody RemoveLinkRequest request) {
        return ResponseEntity.ok(commandService.deleteLink(tgChatId, request));
    }
}
