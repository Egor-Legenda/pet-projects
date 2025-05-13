package dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import backend.academy.common.dto.request.AddLinkRequest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddLinkRequestTest {

    private AddLinkRequest addLinkRequest;

    @BeforeEach
    void setUp() {
        addLinkRequest = new AddLinkRequest();
    }

    @Test
    void testGetSetLink() {
        // Arrange
        String link = "https://example.com";

        // Act
        addLinkRequest.link(link);

        // Assert
        assertEquals(link, addLinkRequest.link());
    }

    @Test
    void testGetSetTags() {
        // Arrange
        List<String> tags = Arrays.asList("tag1", "tag2");

        // Act
        addLinkRequest.tags(tags);

        // Assert
        assertEquals(tags, addLinkRequest.tags());
    }

    @Test
    void testGetSetFilters() {
        // Arrange
        List<String> filters = Arrays.asList("filter1", "filter2");

        // Act
        addLinkRequest.filters(filters);

        // Assert
        assertEquals(filters, addLinkRequest.filters());
    }
}
