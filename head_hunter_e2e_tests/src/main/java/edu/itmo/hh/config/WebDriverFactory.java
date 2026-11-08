package edu.itmo.hh.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class WebDriverFactory {

    private static final boolean HEADLESS =
            Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "false"));

    private WebDriverFactory() {
    }

    public static WebDriver create(BrowserType browser) {
        return switch (browser) {
            case CHROME -> createChrome();
            case FIREFOX -> createFirefox();
        };
    }

    private static WebDriver createChrome() {
        String driver = getenv("CHROME_DRIVER_PATH");
        if (driver != null) {
            System.setProperty("webdriver.chrome.driver", driver);
        }
        ChromeOptions o = new ChromeOptions();
        o.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (HEADLESS) {
            o.addArguments("--headless=new");
        }
        o.addArguments("--window-size=1920,1080", "--disable-search-engine-choice-screen");
        return new ChromeDriver(o);
    }

    private static WebDriver createFirefox() {
        String driver = getenv("GECKO_DRIVER_PATH");
        if (driver != null) {
            System.setProperty("webdriver.gecko.driver", driver);
        }
        FirefoxOptions o = new FirefoxOptions();
        o.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (HEADLESS) {
            o.addArguments("-headless");
        }
        o.addArguments("--width=1920", "--height=1080");
        return new FirefoxDriver(o);
    }

    private static String getenv(String name) {
        String v = System.getenv(name);
        return (v == null || v.isBlank()) ? null : v;
    }
}
