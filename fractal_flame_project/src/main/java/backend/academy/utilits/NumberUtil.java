package backend.academy.utilits;

import lombok.Getter;


@Getter
public enum NumberUtil {
    MILLION(1000000),
    THOUSAND(1000),
    DEGREE(360),
    COLOR_MAX(255),
    SIXTEEN(16),
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIFE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
    START_CYCLE(-20);

    public final int number;

    NumberUtil(int number) {
        this.number = number;
    }
}
