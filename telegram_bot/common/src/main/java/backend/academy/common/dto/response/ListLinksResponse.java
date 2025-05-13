package backend.academy.common.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;

    public ListLinksResponse() {}

    public List<LinkResponse> getLinks() {
        return links;
    }

    public int getSize() {
        return size;
    }

    public void setLinks(List<LinkResponse> links) {
        this.links = links;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
