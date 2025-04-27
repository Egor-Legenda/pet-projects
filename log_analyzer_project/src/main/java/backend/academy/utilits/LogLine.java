package backend.academy.utilits;

import backend.academy.utilits.http.HttpRequest;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor @ToString
public class LogLine {
    @Getter
    private String ip;
    @Getter
    private String name;
    @Getter
    private ZonedDateTime time;
    @Getter
    private HttpRequest httpRequest;
    @Getter
    private Integer status;
    @Getter
    private Integer size;
    @Getter
    private String httpRefer;
    @Getter
    private String userAgent;
}
