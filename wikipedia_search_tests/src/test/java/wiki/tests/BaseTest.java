package wiki.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import wiki.configurations.ConfigTest;
import wiki.pages.MainPage;
import wiki.pages.SearchResultsPage;

import static com.codeborne.selenide.Selenide.open;


public class BaseTest extends ConfigTest {
    protected static MainPage mainPage;
    protected static SearchResultsPage searchResultsPage;

    @BeforeEach
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
        searchResultsPage = new SearchResultsPage();
        mainPage = new MainPage();
        open("https://www.wikipedia.org/");
    }
}
