package backend.academy.scrapper.repository;

import static org.junit.jupiter.api.Assertions.*;

import backend.academy.common.dto.response.LinkResponse;
import backend.academy.scrapper.repository.jdbc.JdbcRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.datasource.access-type=jdbc")
@Transactional
class JdbcRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcRepository jdbcRepository;

    @Test
    void addAndGetLink() {
        // given
        Long chatId = 999L;
        jdbcRepository.registerChat(chatId);
        LinkResponse link =
                new LinkResponse(null, "https://example.com", List.of("java", "backend"), List.of("filter1"));

        // when
        jdbcRepository.addLink(chatId, link);
        List<LinkResponse> links = jdbcRepository.getLinks(chatId);

        // then
        assertEquals(1, links.size());
        LinkResponse storedLink = links.get(0);
        assertEquals("https://example.com", storedLink.getUrl());
        assertIterableEquals(List.of("java", "backend"), storedLink.getTags());
        assertIterableEquals(List.of("filter1"), storedLink.getFilters());
    }

    @Test
    void deleteLink() {
        // given
        Long chatId = 1234L;
        jdbcRepository.registerChat(chatId);
        LinkResponse link = new LinkResponse(null, "https://delete-me.com", List.of(), List.of());
        jdbcRepository.addLink(chatId, link);

        assertEquals(1, jdbcRepository.getLinks(chatId).size());

        // when
        jdbcRepository.deleteLink(chatId, link);

        // then
        assertTrue(jdbcRepository.getLinks(chatId).isEmpty());
    }

    @Test
    void checkLinkExistsReturnsTrue() {
        // given
        Long chatId = 5678L;
        jdbcRepository.registerChat(chatId);
        LinkResponse link = new LinkResponse(null, "https://checklink.com", List.of(), List.of());
        jdbcRepository.addLink(chatId, link);
        // when
        boolean exists = jdbcRepository.checkLink(chatId, "https://checklink.com");
        // then
        assertTrue(exists);
    }

    @Test
    void updateLink() {
        // given
        Long chatId = 9999L;
        jdbcRepository.registerChat(chatId);
        LinkResponse link = new LinkResponse(null, "https://update-me.com", List.of("oldTag"), List.of("oldFilter"));
        jdbcRepository.addLink(chatId, link);

        // when
        LinkResponse updatedLink =
                new LinkResponse(null, "https://update-me.com", List.of("newTag"), List.of("newFilter"));
        jdbcRepository.updateLink(chatId, updatedLink);

        List<LinkResponse> links = jdbcRepository.getLinks(chatId);
        assertEquals(1, links.size());
        LinkResponse storedLink = links.get(0);
        // then
        assertEquals(1, links.size());
        assertEquals("https://update-me.com", storedLink.getUrl());
    }

    @Test
    void checkLinkNotExistsReturnsFalse() {
        // given
        Long chatId = 777L;
        jdbcRepository.registerChat(chatId);
        // when
        boolean exists = jdbcRepository.checkLink(chatId, "https://checklink.com");
        // then
        assertFalse(exists);
    }
}
