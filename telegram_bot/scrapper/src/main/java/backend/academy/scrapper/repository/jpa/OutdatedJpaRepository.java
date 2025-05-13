package backend.academy.scrapper.repository.jpa;

import backend.academy.common.dto.response.LinkResponse;
import backend.academy.scrapper.entity.Filter;
import backend.academy.scrapper.entity.Link;
import backend.academy.scrapper.entity.Tag;
import backend.academy.scrapper.entity.User;
import backend.academy.scrapper.repository.custom.CustomRepository;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OutdatedJpaRepository implements CustomRepository {
    private final SessionFactory factory;

    @Autowired
    public OutdatedJpaRepository(EntityManagerFactory entityManagerFactory) {
        this.factory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @PreDestroy
    public void close() {
        if (factory != null) {
            factory.close();
        }
    }

    @Override
    public List<LinkResponse> getLinks(Long chatId) {
        try (Session session = factory.openSession()) {
            User user = session.createQuery("FROM User WHERE chatId = :chatId", User.class)
                    .setParameter("chatId", chatId)
                    .uniqueResult();

            if (user == null) {
                return List.of();
            }

            return user.getLinks().stream()
                    .map(link -> new LinkResponse(
                            link.getUser().getChatId(),
                            link.getUrl(),
                            link.getTags().stream().map(Tag::getName).toList(),
                            link.getFilters().stream().map(Filter::getName).toList(),
                            link.getCreatedAt(),
                            link.getUpdateTime()))
                    .toList();
        }
    }

    @Override
    public void addLink(Long chatId, LinkResponse linkResponse) {
        Transaction txn = null;
        try (Session session = factory.openSession()) {
            txn = session.beginTransaction();

            User user = session.createQuery("FROM User WHERE chatId = :chatId", User.class)
                    .setParameter("chatId", chatId)
                    .uniqueResult();

            if (user == null) {
                user = new User();
                user.setChatId(chatId);
                session.persist(user);
            }

            Link link = new Link();
            link.setUser(user);
            link.setUrl(linkResponse.getUrl());

            link.setTags(fetchOrCreateTags(linkResponse.getTags(), session));
            link.setFilters(fetchOrCreateFilters(linkResponse.getFilters(), session));

            session.persist(link);
            txn.commit();
        } catch (Exception ex) {
            if (txn != null) txn.rollback();
            ex.printStackTrace();
        }
    }

    private Set<Tag> fetchOrCreateTags(List<String> tagNames, Session session) {
        return tagNames.stream()
                .map(name -> session.createQuery("FROM Tag WHERE name = :name", Tag.class)
                        .setParameter("name", name)
                        .uniqueResultOptional()
                        .orElseGet(() -> {
                            Tag tag = new Tag();
                            tag.setName(name);
                            session.persist(tag);
                            return tag;
                        }))
                .collect(Collectors.toSet());
    }

    private Set<Filter> fetchOrCreateFilters(List<String> filterNames, Session session) {
        return filterNames.stream()
                .map(name -> session.createQuery("FROM Filter WHERE name = :name", Filter.class)
                        .setParameter("name", name)
                        .uniqueResultOptional()
                        .orElseGet(() -> {
                            Filter filter = new Filter();
                            filter.setName(name);
                            session.persist(filter);
                            return filter;
                        }))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteLink(Long chatId, LinkResponse linkResponse) {
        Transaction txn = null;
        try (Session session = factory.openSession()) {
            txn = session.beginTransaction();

            Link link = session.createQuery("FROM Link WHERE url = :url AND user.chatId = :chatId", Link.class)
                    .setParameter("url", linkResponse.getUrl())
                    .setParameter("chatId", chatId)
                    .uniqueResult();

            if (link != null) {
                session.remove(link);
            }

            txn.commit();
        } catch (Exception ex) {
            if (txn != null) txn.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public boolean checkLink(Long chatId, String link) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "SELECT count(l) > 0 FROM Link l WHERE l.url = :url AND l.user.chatId = :chatId",
                            Boolean.class)
                    .setParameter("url", link)
                    .setParameter("chatId", chatId)
                    .uniqueResult();
        }
    }

    @Override
    public void deleteAllLinks(Long chatId) {
        Transaction txn = null;
        try (Session session = factory.openSession()) {
            txn = session.beginTransaction();
            session.createQuery("DELETE FROM Link WHERE user.chatId = :chatId")
                    .setParameter("chatId", chatId)
                    .executeUpdate();
            txn.commit();
        } catch (Exception ex) {
            if (txn != null) txn.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void updateLink(Long chatId, LinkResponse linkResponse) {
        Transaction txn = null;
        try (Session session = factory.openSession()) {
            txn = session.beginTransaction();

            Link link = session.createQuery("FROM Link WHERE url = :url AND user.chatId = :chatId", Link.class)
                    .setParameter("url", linkResponse.getUrl())
                    .setParameter("chatId", chatId)
                    .uniqueResult();

            if (link != null) {
                link.setUrl(linkResponse.getUrl());
                link.setTags(fetchOrCreateTags(linkResponse.getTags(), session));
                link.setFilters(fetchOrCreateFilters(linkResponse.getFilters(), session));
                session.persist(link);
            }

            txn.commit();
        } catch (Exception ex) {
            if (txn != null) txn.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public boolean chatExists(Long chatId) {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT count(u) > 0 FROM User u WHERE u.chatId = :chatId", Boolean.class)
                    .setParameter("chatId", chatId)
                    .uniqueResult();
        }
    }

    @Override
    public void registerChat(Long chatId) {
        Transaction txn = null;
        try (Session session = factory.openSession()) {
            txn = session.beginTransaction();
            User user = new User();
            user.setChatId(chatId);
            session.persist(user);
            txn.commit();
        } catch (Exception ex) {
            if (txn != null) txn.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteChat(Long chatId) {
        Transaction txn = null;
        try (Session session = factory.openSession()) {
            txn = session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE chatId = :chatId")
                    .setParameter("chatId", chatId)
                    .executeUpdate();
            txn.commit();
        } catch (Exception ex) {
            if (txn != null) txn.rollback();
            ex.printStackTrace();
        }
    }
}
