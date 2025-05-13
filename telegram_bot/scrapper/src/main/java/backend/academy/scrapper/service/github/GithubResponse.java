package backend.academy.scrapper.service.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class GithubResponse {
    @JsonProperty("created_at")
    private ZonedDateTime created_at;

    @JsonProperty("updated_at")
    private ZonedDateTime updated_at;

    @JsonProperty("pushed_at")
    private ZonedDateTime pushed_at;

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public ZonedDateTime getPushed_at() {
        return pushed_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void setPushed_at(ZonedDateTime pushed_at) {
        this.pushed_at = pushed_at;
    }
}
