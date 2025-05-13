package backend.academy.scrapper.service.stackOverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

// @Data - не работает
public class QuestionResponse {
    @JsonProperty("question_id")
    private String questionId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("creation_date")
    private ZonedDateTime creationDate;

    @JsonProperty("last_activity_date")
    private ZonedDateTime lastActivityDate;

    @JsonProperty("link")
    private String link;

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastActivityDate(ZonedDateTime lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public ZonedDateTime getLastActivityDate() {
        return lastActivityDate;
    }

    public String getLink() {
        return link;
    }
}
