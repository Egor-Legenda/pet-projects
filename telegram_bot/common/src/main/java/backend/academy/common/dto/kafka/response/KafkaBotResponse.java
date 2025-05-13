package backend.academy.common.dto.kafka.response;

import backend.academy.common.dto.enums.CommandType;
import backend.academy.common.dto.enums.ResponseType;
import backend.academy.common.dto.kafka.KafkaMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaBotResponse extends KafkaMessage {
    @JsonProperty("commandType")
    private CommandType commandType;

    @JsonProperty("responseType")
    private ResponseType responseType;

    @JsonProperty("message")
    private String message;

    public KafkaBotResponse(
            @JsonProperty("chatId") Long chatId,
            @JsonProperty("commandType") CommandType commandType,
            @JsonProperty("responseType") ResponseType responseType,
            @JsonProperty("message") String message) {
        super(chatId);
        this.commandType = commandType;
        this.responseType = responseType;
        this.message = message;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
