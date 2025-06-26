package wiki.configurations;

import com.codeborne.selenide.Configuration;

public class ConfigTest {
    static {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 10000;
    }
}

