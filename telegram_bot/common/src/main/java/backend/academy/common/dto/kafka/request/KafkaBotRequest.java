package backend.academy.common.dto.kafka.request;

import backend.academy.common.dto.enums.CommandType;
import backend.academy.common.dto.kafka.KafkaMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class KafkaBotRequest extends KafkaMessage {
    @JsonProperty("commandType") // Указывает имя поля в JSON
    private CommandType commandType;

    @JsonProperty("link")
    private String link;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("filters")
    private List<String> filters;

    // Аннотированный конструктор для десериализации
    @JsonCreator
    public KafkaBotRequest(
            @JsonProperty("chatId") Long chatId,
            @JsonProperty("commandType") CommandType commandType,
            @JsonProperty("link") String link,
            @JsonProperty("filters") List<String> filters,
            @JsonProperty("tags") List<String> tags) {
        super(chatId);
        this.commandType = commandType;
        this.link = link;
        this.filters = filters;
        this.tags = tags;
    }

    public KafkaBotRequest(Long chatId, CommandType commandType) {
        super(chatId);
        this.commandType = commandType;
    }

    public KafkaBotRequest(Long chatId, CommandType commandType, String link) {
        super(chatId);
        this.commandType = commandType;
        this.link = link;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }
}
