package backend.academy.scrapper.repository.jpa;

import backend.academy.common.dto.response.LinkResponse;
import backend.academy.scrapper.entity.Filter;
import backend.academy.scrapper.entity.Link;
import backend.academy.scrapper.entity.Tag;
import backend.academy.scrapper.entity.User;
import backend.academy.scrapper.repository.custom.CustomRepository;
import backend.academy.scrapper.repository.interfaces.FilterRepository;
import backend.academy.scrapper.repository.interfaces.LinkRepository;
import backend.academy.scrapper.repository.interfaces.TagRepository;
import backend.academy.scrapper.repository.interfaces.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JpaRepository implements CustomRepository {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FilterRepository filterRepository;

    @Override
    public List<LinkResponse> getLinks(Long chatId) {
        return linkRepository.findAllByUser_ChatId(chatId).stream()
                .map(link -> new LinkResponse(
                        chatId,
                        link.getUrl(),
                        link.getTags().stream().map(Tag::getName).toList(),
                        link.getFilters().stream().map(Filter::getName).toList(),
                        link.getCreatedAt(),
                        link.getUpdateTime()))
                .toList();
    }

    @Override
    public void addLink(Long chatId, LinkResponse linkResponse) {
        User user = userRepository.findByChatId(chatId).orElse(null);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            user = userRepository.save(user);
        }

        var link = new Link();
        link.setUser(user);
        link.setUrl(linkResponse.getUrl());
        link.setTags(fetchOrCreateTags(linkResponse.getTags()));
        link.setFilters(fetchOrCreateFilters(linkResponse.getFilters()));

        linkRepository.save(link);
    }

    private Set<Tag> fetchOrCreateTags(List<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setName(name);
                return tagRepository.save(newTag);
            });
            tags.add(tag);
        }
        return tags;
    }

    private Set<Filter> fetchOrCreateFilters(List<String> filterNames) {
        Set<Filter> filters = new HashSet<>();
        for (String name : filterNames) {
            Filter filter = filterRepository.findByName(name).orElseGet(() -> {
                Filter newFilter = new Filter();
                newFilter.setName(name);
                return filterRepository.save(newFilter);
            });
            filters.add(filter);
        }
        return filters;
    }

    @Override
    public void deleteLink(Long chatId, LinkResponse linkResponse) {
        var linkOpt = linkRepository.findByUrlAndUser_ChatId(linkResponse.getUrl(), chatId);
        linkOpt.ifPresent(linkRepository::delete);
    }

    @Override
    public boolean checkLink(Long chatId, String url) {
        return linkRepository.findByUrlAndUser_ChatId(url, chatId).isPresent();
    }

    @Override
    public void deleteAllLinks(Long chatId) {
        linkRepository.deleteAllByUser_ChatId(chatId);
    }

    @Override
    public void updateLink(Long chatId, LinkResponse linkResponse) {
        var linkOpt = linkRepository.findByUrlAndUser_ChatId(linkResponse.getUrl(), chatId);
        linkOpt.ifPresent(link -> {
            link.setTags(fetchOrCreateTags(linkResponse.getTags()));
            link.setFilters(fetchOrCreateFilters(linkResponse.getFilters()));
            linkRepository.save(link);
        });
    }

    @Override
    public boolean chatExists(Long chatId) {
        return userRepository.findByChatId(chatId).isPresent();
    }

    @Override
    public void registerChat(Long chatId) {
        if (!chatExists(chatId)) {
            User user = new User();
            user.setChatId(chatId);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteChat(Long chatId) {
        userRepository.findByChatId(chatId).ifPresent(userRepository::delete);
    }
}
