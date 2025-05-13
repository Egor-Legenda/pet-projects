package backend.academy.common.dto.enums;

import org.springframework.http.HttpStatus;

public enum ResponseType {
    SUCCESS(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    UPDATED(HttpStatus.OK),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    ALREADY_EXISTS(HttpStatus.CONFLICT),
    INFO(HttpStatus.ACCEPTED),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    CHAT_ALREADY_EXISTS(HttpStatus.OK);

    private final HttpStatus httpStatus;

    ResponseType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
