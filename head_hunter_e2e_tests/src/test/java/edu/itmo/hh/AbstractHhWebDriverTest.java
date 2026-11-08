package edu.itmo.hh;

import edu.itmo.hh.config.WebDriverFactory;
import edu.itmo.hh.support.BrowserTestSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
public abstract class AbstractHhWebDriverTest {

    protected WebDriver driver;

    @BeforeAll
    void startBrowser() {
        driver = WebDriverFactory.create(BrowserTestSupport.resolveBrowserTypeForSession());
    }

    @AfterAll
    void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
