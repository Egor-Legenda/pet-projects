package backend.academy.common.dto.response;

import backend.academy.common.dto.enums.ResponseType;
import java.time.Instant;

public record ApiResponse<T>(ResponseType status, String message, T data, Instant timestamp) {

    public ApiResponse(ResponseType status, String message, T data) {
        this(status, message, data, Instant.now());
    }
}
