package backend.academy.scrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import backend.academy.scrapper.service.github.ResourceData;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResourceDataTest {

    private ResourceData resourceData;
    private ZonedDateTime lastUpdateTime;
    private ZonedDateTime createTime;

    @BeforeEach
    void setUp() {
        // Arrange
        lastUpdateTime = ZonedDateTime.now();
        createTime = ZonedDateTime.now().minusDays(1);
        resourceData = new ResourceData(lastUpdateTime, createTime);
    }

    @Test
    void testGetLastUpdateTime() {

        // Act
        ZonedDateTime actualLastUpdateTime = resourceData.getLastUpdateTime();

        // Assert
        assertEquals(lastUpdateTime, actualLastUpdateTime);
    }

    @Test
    void testGetCreateTime() {
        // Arrange

        // Act
        ZonedDateTime actualCreateTime = resourceData.getCreateTime();

        // Assert
        assertEquals(createTime, actualCreateTime);
    }

    @Test
    void testSetLastUpdateTime() {
        // Arrange
        ZonedDateTime newLastUpdateTime = ZonedDateTime.now().plusDays(1);

        // Act
        resourceData.setLastUpdateTime(newLastUpdateTime);

        // Assert
        assertEquals(newLastUpdateTime, resourceData.getLastUpdateTime());
    }

    @Test
    void testSetCreateTime() {
        // Arrange
        ZonedDateTime newCreateTime = ZonedDateTime.now().plusDays(2);

        // Act
        resourceData.setCreateTime(newCreateTime);

        // Assert
        assertEquals(newCreateTime, resourceData.getCreateTime());
    }

    @Test
    void testConstructorWithNullValues() {
        // Arrange

        // Act
        ResourceData nullData = new ResourceData(null, null);

        // Assert
        assertNull(
                nullData.getLastUpdateTime(), "LastUpdateTime должен быть null при создании через конструктор с null");
        assertNull(nullData.getCreateTime(), "CreateTime должен быть null при создании через конструктор с null");
    }

    @Test
    void testSetNullValues() {
        // Arrange

        // Act
        resourceData.setLastUpdateTime(null);
        resourceData.setCreateTime(null);

        // Assert
        assertNull(resourceData.getLastUpdateTime(), "LastUpdateTime должен поддерживать null через setter");
        assertNull(resourceData.getCreateTime(), "CreateTime должен поддерживать null через setter");
    }
}
