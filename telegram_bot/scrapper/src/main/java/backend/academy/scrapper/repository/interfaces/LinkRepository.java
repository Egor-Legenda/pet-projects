package backend.academy.scrapper.repository.interfaces;

import backend.academy.scrapper.entity.Link;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUrlAndUser_ChatId(String url, Long chatId);

    List<Link> findAllByUser_ChatId(Long chatId);

    void deleteAllByUser_ChatId(Long chatId);
}
