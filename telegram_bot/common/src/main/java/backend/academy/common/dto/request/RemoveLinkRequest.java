package backend.academy.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveLinkRequest {
    private String link;

    public RemoveLinkRequest(String link) {
        this.link = link;
    }

    public RemoveLinkRequest() {}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
