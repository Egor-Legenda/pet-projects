package backend.academy.scrapper;

import backend.academy.scrapper.repository.custom.CustomRepository;
import backend.academy.scrapper.repository.jdbc.JdbcRepository;
import backend.academy.scrapper.repository.jpa.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RepositoryConfig {

    @Autowired
    private DatabaseConfig databaseConfig;

    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.datasource.access-type", havingValue = "jpa", matchIfMissing = true)
    public CustomRepository jpaUserRepository(JpaRepository jpaRepository) {
        return jpaRepository;
    }

    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.datasource.access-type", havingValue = "jdbc")
    public CustomRepository jdbcUserRepository() {
        return new JdbcRepository(databaseConfig);
    }
}
