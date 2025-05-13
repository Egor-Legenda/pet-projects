package backend.academy.scrapper.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.academy.common.dto.request.AddLinkRequest;
import backend.academy.common.dto.response.ApiResponse;
import backend.academy.common.dto.response.LinkResponse;
import backend.academy.scrapper.repository.custom.CustomRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommandServiceTest {

    @Mock
    private CustomRepository customRepository;

    @InjectMocks
    private CommandService commandService;

    @Test
    void addCorrectLinkTest() {
        // Arrange
        Long chatId = 123L;
        String testUrl = "https://github.com/egor-legenda/itmo_projects";
        List<String> tags = List.of("news", "tech");
        List<String> filters = List.of("filter1", "filter2");
        AddLinkRequest request = new AddLinkRequest(testUrl, filters, tags);
        when(customRepository.checkLink(chatId, testUrl)).thenReturn(false);
        doNothing().when(customRepository).addLink(eq(chatId), any(LinkResponse.class));

        // Act
        ApiResponse<LinkResponse> response = commandService.addLink(chatId, request);

        // Assert
        verify(customRepository).addLink(anyLong(), any(LinkResponse.class));
        assertNotNull(response);
        assertEquals(chatId, response.data().getId());
        assertEquals(testUrl, response.data().getUrl());
        assertEquals(tags, response.data().getTags());
        assertEquals(filters, response.data().getFilters());
    }

    @Test
    void updateCorrectLinkTest() {
        // Arrange
        Long chatId = 123L;
        String testUrl = "https://github.com/egor-legenda/itmo_projects";
        List<String> tags = List.of("news", "tech");
        List<String> filters = List.of("filter1", "filter2");
        AddLinkRequest request = new AddLinkRequest(testUrl, filters, tags);
        when(customRepository.checkLink(chatId, testUrl)).thenReturn(true);
        doNothing().when(customRepository).updateLink(eq(chatId), any(LinkResponse.class));

        // Act
        ApiResponse<LinkResponse> response = commandService.addLink(chatId, request);

        // Assert
        verify(customRepository).updateLink(anyLong(), any(LinkResponse.class));
        assertNotNull(response);
        assertEquals(chatId, response.data().getId());
        assertEquals(testUrl, response.data().getUrl());
        assertEquals(tags, response.data().getTags());
        assertEquals(filters, response.data().getFilters());
    }
}
