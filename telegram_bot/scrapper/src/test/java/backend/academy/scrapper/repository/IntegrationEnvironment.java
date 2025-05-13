package backend.academy.scrapper.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
public abstract class IntegrationEnvironment {

    @Container
    protected static final PostgreSQLContainer<?> SQL_CONTAINER = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    static {
        SQL_CONTAINER.start();
        runLiquibaseMigrations();
    }

    private static void runLiquibaseMigrations() {
        try (Connection connection = DriverManager.getConnection(
                SQL_CONTAINER.getJdbcUrl(), SQL_CONTAINER.getUsername(), SQL_CONTAINER.getPassword())) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase =
                    new Liquibase("migrations/changelog-master.xml", new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());

        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException("Failed to run Liquibase migrations", e);
        }
    }

    @DynamicPropertySource
    static void registerDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", SQL_CONTAINER::getPassword);
        registry.add("spring.liquibase.enabled", () -> "true");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }
}
