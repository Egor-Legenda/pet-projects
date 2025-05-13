package backend.academy.scrapper.storage;

import java.time.ZonedDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserLinkStorage {

    private static final Logger log = LoggerFactory.getLogger(UserLinkStorage.class);
    private static final Map<Long, List<LinkData>> userLinksMap = new HashMap<>();

    public void addLink(Long userId, String link, List<String> tags, List<String> filters) {

        List<LinkData> linkList = userLinksMap.getOrDefault(userId, new LinkedList<>());

        linkList.add(new LinkData(link, tags, filters, ZonedDateTime.now()));

        userLinksMap.put(userId, linkList);
        log.atInfo()
                .setMessage("Adding link")
                .addKeyValue("chat-id", userId)
                .addKeyValue("link", link)
                .addKeyValue("tags", tags)
                .addKeyValue("filters", filters)
                .log();
        //        userLinksMap.computeIfAbsent(userId, k -> new ArrayList<>())
        //            .add(new LinkData(link, tags, filters));

    }

    public boolean checkLinkFromUser(Long userId, String link) {
        if (userLinksMap.containsKey(userId)) {
            for (LinkData l : userLinksMap.get(userId)) {
                if (l.getUrl().equals(link)) {
                    log.atInfo()
                            .setMessage("Link already exists")
                            .addKeyValue("chat-id", userId)
                            .addKeyValue("link", link)
                            .log();
                    return true;
                }
            }
        }
        log.atInfo()
                .setMessage("Link does not exist")
                .addKeyValue("chat-id", userId)
                .addKeyValue("link", link)
                .log();
        return false;
    }

    public List<LinkData> getLinks(Long userId) {
        return userLinksMap.getOrDefault(userId, Collections.emptyList());
    }

    public boolean removeLink(Long userId, String link) {
        if (userLinksMap.containsKey(userId)) {
            log.atInfo()
                    .setMessage("Link removed")
                    .addKeyValue("chat-id", userId)
                    .addKeyValue("link", link)
                    .log();
            return userLinksMap.get(userId).removeIf(linkData -> linkData.getUrl()
                    .equals(link));
        }
        log.atInfo()
                .setMessage("Link not found")
                .addKeyValue("chat-id", userId)
                .addKeyValue("link", link)
                .log();
        return false;
    }

    public Map<Long, List<LinkData>> getUserLinksMap() {
        return userLinksMap;
    }

    public void addEmptyList(Long userId) {
        userLinksMap.put(userId, new LinkedList<>());
    }

    public void clearUserLinks(Long userId) {
        userLinksMap.remove(userId);
    }

    public boolean hasLinks(Long userId) {
        return userLinksMap.containsKey(userId) && !userLinksMap.get(userId).isEmpty();
    }

    public int getLinkCount(Long userId) {
        return userLinksMap.getOrDefault(userId, Collections.emptyList()).size();
    }
}
