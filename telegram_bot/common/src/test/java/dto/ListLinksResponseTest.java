package dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import backend.academy.common.dto.response.LinkResponse;
import backend.academy.common.dto.response.ListLinksResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListLinksResponseTest {

    private ListLinksResponse listLinksResponse;
    private List<LinkResponse> testLinks;
    private final int testSize = 5;

    @BeforeEach
    void setUp() {
        // Arrange
        listLinksResponse = new ListLinksResponse();
        testLinks = Arrays.asList(
                new LinkResponse(1L, "https://example.com", Arrays.asList("tag1"), Arrays.asList("filter1")),
                new LinkResponse(2L, "https://anotherexample.com", Arrays.asList("tag2"), Arrays.asList("filter2")));
    }

    @Test
    void testGetSetLinks() {
        // Arrange

        // Act
        listLinksResponse.links(testLinks);
        List<LinkResponse> actualLinks = listLinksResponse.links();

        // Assert
        assertEquals(testLinks, actualLinks, "Список ссылок должен соответствовать установленному");
    }

    @Test
    void testGetSetSize() {
        // Arrange

        // Act
        listLinksResponse.size(testSize);
        int actualSize = listLinksResponse.size();

        // Assert
        assertEquals(testSize, actualSize, "Размер должен соответствовать установленному");
    }
}
