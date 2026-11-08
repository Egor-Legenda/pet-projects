package edu.itmo.hh.support;

import edu.itmo.hh.config.BrowserType;
import java.util.Locale;

public final class BrowserTestSupport {

    private static final String PROP = "browser";

    private BrowserTestSupport() {
    }

    public static BrowserType resolveBrowserTypeForSession() {
        String raw = System.getProperty(PROP, "chrome").trim().toLowerCase(Locale.ROOT);
        return switch (raw) {
            case "firefox", "ff" -> BrowserType.FIREFOX;
            default -> BrowserType.CHROME;
        };
    }
}
