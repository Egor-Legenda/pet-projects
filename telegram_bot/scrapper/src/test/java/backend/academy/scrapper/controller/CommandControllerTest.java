package backend.academy.scrapper.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import backend.academy.common.dto.request.AddLinkRequest;
import backend.academy.scrapper.controllers.CommandController;
import backend.academy.scrapper.service.CommandService;
import backend.academy.scrapper.service.UpdateMonitoringService;
import backend.academy.scrapper.storage.UserLinkStorage;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CommandControllerTest {

    @Test
    void addInvalidLink() {
        // Arrange
        CommandService commandService = mock(CommandService.class);
        UpdateMonitoringService updateMonitoringService = mock(UpdateMonitoringService.class);
        UserLinkStorage userLinkStorage = mock(UserLinkStorage.class);
        CommandController controller = new CommandController(commandService, userLinkStorage, updateMonitoringService);
        Long chatId = 123L;
        AddLinkRequest request = new AddLinkRequest("invalid-url", List.of(), List.of());
        when(updateMonitoringService.checkLink(request.link())).thenReturn(false);

        // Act
        ResponseEntity<?> response = controller.addLink(chatId, request);

        // Assert
        assertEquals(404, response.getStatusCode().value());
    }
}
