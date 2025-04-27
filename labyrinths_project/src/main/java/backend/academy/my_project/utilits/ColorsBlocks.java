package backend.academy.my_project.utilits;

import java.util.Arrays;
import lombok.Getter;

public enum ColorsBlocks {
    BLACK_BLOCK("BLACK_BLOCK", 0, "\u2B1B"),
    WHITE_BLOCK("WHITE_BLOCK", 1, "\u2B1C"),
    GREEN_BLOCK("GREEN_BLOCK", 2, "\uD83D\uDFE2"),
    CYAN_BLOCK("CYAN_BLOCK", 3, "\uD83D\uDFE1"),
    BLUE_BLOCK("BLUE_BLOCK", 4, "\uD83D\uDFE6"),
    RED_BLOCK("RED_BLOCK", 5, "\uD83D\uDFE5"),
    YELLOW_BLOCK("YELLOW_BLOCK", 6, "\uD83D\uDFE8"),
    ORANGE_BLOCK("ORANGE_BLOCK", 7, "\uD83D\uDFE7"),
    BROWN_BLOCK("BROWN_BLOCK", 8, "\uD83D\uDFEB"),
    PURPLE_BLOCK("PURPLE_BLOCK", 9, "\uD83D\uDFE3");

    @Getter
    private String colors;
    @Getter
    private Integer code;
    @Getter
    private String symbol;


    ColorsBlocks(String name, Integer code, String symbol) {
        this.colors = name;
        this.code = code;
        this.symbol = symbol;
    }


    public static String getSymbolByCode(int code) {
        return Arrays.stream(ColorsBlocks.values())
            .filter(block -> block.code() == code)
            .map(ColorsBlocks::symbol)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }
}
