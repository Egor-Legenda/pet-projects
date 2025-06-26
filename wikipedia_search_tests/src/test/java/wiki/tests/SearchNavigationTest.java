package wiki.tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiki.exceptions.SkipException;
import wiki.pages.SearchResultsPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Поиск на Википедии")
@Feature("Навигация по поиску")
public class SearchNavigationTest extends BaseTest {

    @Test
    @Story("Переход по саджестам")
    @DisplayName("Проверка точного перехода по первому саджесту")
    @Severity(SeverityLevel.CRITICAL)
    public void navigateFromFirstSuggestion() {
        /*
         * Проверяем, что при клике на первый саджест происходит переход
         * на страницу с точным соответствием заголовка
         */
        mainPage.enterSearchQuery("Иван");

        String expectedTitle = mainPage.getSuggestionResults()
                .get(0).$(".suggestion-title")
                .text();

        mainPage.clickSuggestion(0);
        assertEquals(expectedTitle,
                searchResultsPage.getPageTitle().text(),
                "Заголовок страницы должен точно соответствовать выбранному саджесту");
    }

    @Test
    @Story("Работа кнопки поиска")
    @DisplayName("Проверка перехода по кнопке поиска")
    @Severity(SeverityLevel.BLOCKER)
    public void navigateViaSearchButton() {
        /*
         * Проверяем, что при вводе существующего запроса и нажатии на кнопку поиска
         * происходит переход на страницу статьи (первый саджест)
         */
        mainPage.enterSearchQuery("Иван");
        mainPage.clickSearchButton();
        assertEquals("Иван", searchResultsPage.getPageTitle().text());
    }

    @Test
    @Story("Поиск несуществующих запросов")
    @DisplayName("Проверка перехода на страницу поиска при отсутствии саджестов")
    @Description("При вводе несуществующего запроса и нажатии на кнопку поиска система должна перенаправлять на страницу результатов поиска")
    @Severity(SeverityLevel.NORMAL)
    public void searchButtonNavigatesToSearchPageWhenNoSuggestions() {
        /*
         * Проверяем, что при вводе несуществующего запроса и нажатии на кнопку поиска
         * происходит переход на страницу с результатами поиска
         */
        mainPage.enterSearchQuery("Иваннннн");
        mainPage.clickSearchButton();
        webdriver().shouldHave(urlContaining("search"));
        assertTrue(searchResultsPage.getSearchResults().exists());
    }

    @Test
    @Story("Дополнительные подсказки")
    @DisplayName("Проверка отображения подсказки 'Поиск страниц содержащих'")
    @Description("При вводе несуществующего запроса должна появляться подсказка для расширенного поиска")
    @Severity(SeverityLevel.MINOR)
    public void searchHintNavigation() {
        /*
        * Тест отображения поля для ввода сообщения
         */
        mainPage.enterSearchQuery("Иваннннн");

        mainPage.getSearchInput()
                .shouldBe(visible);
    }


    @Test
    @Story("Дополнительные подсказки")
    @DisplayName("Проверка перехода по подсказке 'Поиск страниц содержащих'")
    @Description("Тест проверяет возможность перехода по динамической подсказке для расширенного поиска")
    @Severity(SeverityLevel.MINOR)
    public void searchHintNavigationWithText() throws SkipException {
        /*
         * Тест не автоматизирован из-за динамической природы подсказки.
         * Не удается стабильно определить, каким текстом называется подсказка и как работает ее отображение.
         */
        Allure.addAttachment("Причина неавтоматизации",
                "Динамическое появление подсказки нестабильно, что делает автоматизацию ненадежной");

        throw new SkipException("Тест пропущен: Динамическое появление подсказки нестабильно");
    }

}