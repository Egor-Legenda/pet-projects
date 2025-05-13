package dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import backend.academy.common.dto.response.LinkResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkResponseTest {

    private LinkResponse linkResponse;
    private final List<String> initialTags = Arrays.asList("tag1", "tag2");
    private final List<String> initialFilters = Arrays.asList("filter1", "filter2");
    private final Long initialId = 1L;
    private final String initialUrl = "https://example.com";

    @BeforeEach
    void setUp() {
        // Arrange - общая настройка для всех тестов
        linkResponse = new LinkResponse(initialId, initialUrl, initialTags, initialFilters);
    }

    @Test
    void testGetId() {
        // Act
        Long actualId = linkResponse.id();

        // Assert
        assertEquals(initialId, actualId);
    }

    @Test
    void testGetUrl() {
        // Act
        String actualUrl = linkResponse.url();

        // Assert
        assertEquals(initialUrl, actualUrl);
    }

    @Test
    void testGetTags() {
        // Act
        List<String> actualTags = linkResponse.tags();

        // Assert
        assertEquals(initialTags, actualTags);
    }

    @Test
    void testGetFilters() {
        // Act
        List<String> actualFilters = linkResponse.filters();

        // Assert
        assertEquals(initialFilters, actualFilters);
    }

    @Test
    void testSetId() {
        // Arrange
        Long newId = 2L;

        // Act
        linkResponse.id(newId);

        // Assert
        assertEquals(newId, linkResponse.id());
    }

    @Test
    void testSetUrl() {
        // Arrange
        String newUrl = "https://anotherexample.com";

        // Act
        linkResponse.url(newUrl);

        // Assert
        assertEquals(newUrl, linkResponse.url());
    }

    @Test
    void testSetTags() {
        // Arrange
        List<String> newTags = Arrays.asList("tag3", "tag4");

        // Act
        linkResponse.tags(newTags);

        // Assert
        assertEquals(newTags, linkResponse.tags());
    }

    @Test
    void testSetFilters() {
        // Arrange
        List<String> newFilters = Arrays.asList("filter3", "filter4");

        // Act
        linkResponse.filters(newFilters);

        // Assert
        assertEquals(newFilters, linkResponse.filters());
    }
}
