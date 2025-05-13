package backend.academy.scrapper.storage;

import java.time.ZonedDateTime;
import java.util.List;

// @Data не работает не успел разобраться почему
public class LinkData {
    private String url;
    private List<String> tags;
    private List<String> filters;
    ZonedDateTime lastTimeUpdate;

    public LinkData(String url, List<String> tags, List<String> filters, ZonedDateTime lastTimeUpdate) {
        this.url = url;
        this.tags = tags;
        this.filters = filters;
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public LinkData(String url, List<String> tags, List<String> filters) {
        this.url = url;
        this.tags = tags;
        this.filters = filters;
    }

    public void setLastTimeUpdate(ZonedDateTime lastTimeUpdate) {
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public ZonedDateTime getLastTimeUpdate() {
        return lastTimeUpdate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getFilters() {
        return filters;
    }
}
