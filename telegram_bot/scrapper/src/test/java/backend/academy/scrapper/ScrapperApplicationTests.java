package backend.academy.scrapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

// @Import(TestcontainersConfiguration.class)
@Testcontainers
@SpringBootTest
@TestPropertySource(
        properties = {
            "spring.datasource.url=jdbc:postgresql://localhost:5432/project",
            "spring.datasource.username=postgres",
            "spring.datasource.password=20052005"
        })
class ScrapperApplicationTests {

    //    @Container
    //    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
    //        .withDatabaseName("testdb")
    //        .withUsername("test")
    //        .withPassword("test");
    //
    //    @DynamicPropertySource
    //    static void postgresProperties(DynamicPropertyRegistry registry) {
    //        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    //        registry.add("spring.datasource.username", postgres::getUsername);
    //        registry.add("spring.datasource.password", postgres::getPassword);
    //    }
    //
    //    @Test
    //    void contextLoads() {
    //    }
}
