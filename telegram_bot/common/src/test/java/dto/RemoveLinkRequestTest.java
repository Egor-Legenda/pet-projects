package dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import backend.academy.common.dto.request.RemoveLinkRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemoveLinkRequestTest {

    private RemoveLinkRequest removeLinkRequest;
    private final String testUrl = "https://example.com";

    @BeforeEach
    void setUp() {
        // Arrange
        removeLinkRequest = new RemoveLinkRequest();
    }

    @Test
    void testGetSetLink() {
        // Act
        removeLinkRequest.link(testUrl);

        // Assert
        assertEquals(testUrl, removeLinkRequest.link(), "URL должен соответствовать установленному значению");
    }
}
