package backend.academy.scrapper;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import backend.academy.scrapper.repository.IntegrationEnvironment;
import backend.academy.scrapper.repository.custom.CustomRepository;
import backend.academy.scrapper.repository.jdbc.JdbcRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class JdbcRepositoryConfigTest extends IntegrationEnvironment {
    @Autowired
    CustomRepository customRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.access-type", () -> "jdbc");
    }

    @Test
    void testJpaRepositoryIsUsedWhenAccessTypeIsORM() {

        // Assert
        assertInstanceOf(JdbcRepository.class, customRepository);
    }
}
