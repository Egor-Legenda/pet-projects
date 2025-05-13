package backend.academy.scrapper.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import backend.academy.common.dto.response.LinkResponse;
import backend.academy.scrapper.repository.jpa.JpaRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(properties = "spring.datasource.access-type=jpa")
public class JpaRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaRepository jpaRepository;

    @BeforeEach
    void setup() {
        jpaRepository.deleteChat(999L);
        jpaRepository.registerChat(999L);
    }

    @Test
    void addAndGetLink() {
        // given
        Long chatId = 999L;
        LinkResponse link = new LinkResponse(null, "https://example.com", List.of("java"), List.of("filter1"));

        // when
        jpaRepository.addLink(chatId, link);
        List<LinkResponse> links = jpaRepository.getLinks(chatId);

        // then
        Assertions.assertEquals(1, links.size());
        LinkResponse storedLink = links.get(0);
        Assertions.assertEquals("https://example.com", storedLink.getUrl());
        assertIterableEquals(link.getTags(), storedLink.getTags());
        assertIterableEquals(List.of("filter1"), storedLink.getFilters());
    }

    @Test
    void deleteLink() {
        // given
        Long chatId = 999L;
        String link = "https://example.com/updated";
        LinkResponse linkResponse = new LinkResponse(null, link, List.of("java", "backend"), List.of("filter1"));
        jpaRepository.addLink(chatId, linkResponse);
        assertEquals(1, jpaRepository.getLinks(chatId).size());

        // when
        jpaRepository.deleteLink(chatId, linkResponse);

        // then
        assertEquals(0, jpaRepository.getLinks(chatId).size());
    }

    @Test
    void updateLink() {
        // given
        Long chatId = 999L;
        LinkResponse link =
                new LinkResponse(null, "https://example.com", List.of("java", "backend"), List.of("filter1"));
        jpaRepository.addLink(chatId, link);

        // when
        LinkResponse updatedLink =
                new LinkResponse(null, "https://example.com", List.of("java", "backend"), List.of("filter1"));
        jpaRepository.updateLink(chatId, updatedLink);
        List<LinkResponse> links = jpaRepository.getLinks(chatId);

        // then
        assertEquals(1, links.size());
        LinkResponse storedLink = links.get(0);
        assertEquals("https://example.com", storedLink.getUrl());
    }
}
