package backend.academy.scrapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import backend.academy.scrapper.repository.IntegrationEnvironment;
import backend.academy.scrapper.repository.custom.CustomRepository;
import backend.academy.scrapper.repository.jpa.JpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class JpaRepositoryConfigTest extends IntegrationEnvironment {
    @Autowired
    CustomRepository customRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.access-type", () -> "jpa");
    }

    @Test
    void testJpaRepositoryIsUsedWhenAccessTypeIsORM() {
        // Arrange
        // Act
        Object object = getLinkService();
        // Assert
        assertTrue(object instanceof JpaRepository);
    }

    private Object getLinkService() {
        try {
            var field = JpaRepositoryConfigTest.class.getDeclaredField("customRepository");
            field.setAccessible(true);
            return field.get(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
