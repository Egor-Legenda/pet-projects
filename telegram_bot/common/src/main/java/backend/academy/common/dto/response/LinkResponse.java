package backend.academy.common.dto.response;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkResponse {
    private Long id;
    private String url;
    private List<String> tags;
    private List<String> filters;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    public LinkResponse(String url) {
        this.url = url;
    }

    public LinkResponse(Long id, String url, List<String> tags, List<String> filters) {
        this.id = id;
        this.url = url;
        this.tags = tags;
        this.filters = filters;
    }

    public LinkResponse(
            Long id,
            String url,
            List<String> tags,
            List<String> filters,
            ZonedDateTime created,
            ZonedDateTime updated) {
        this.id = id;
        this.url = url;
        this.tags = tags;
        this.filters = filters;
        this.created = created;
        this.updated = updated;
    }

    public LinkResponse() {}

    public Long getId() {
        return id;
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

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public void setId(Long id) {
        this.id = id;
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
}
