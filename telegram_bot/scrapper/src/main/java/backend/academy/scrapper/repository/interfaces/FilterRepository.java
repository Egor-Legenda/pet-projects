package backend.academy.scrapper.repository.interfaces;

import backend.academy.scrapper.entity.Filter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {
    Optional<Filter> findByName(String name);
}
