package backend.academy.common.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLinkRequest {

    private String link;
    private List<String> tags;
    private List<String> filters;

    public AddLinkRequest(String link, List<String> filters, List<String> tags) {
        this.link = link;
        this.filters = filters;
        this.tags = tags;
    }

    public AddLinkRequest() {}

    public String getLink() {
        return link;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }
}
