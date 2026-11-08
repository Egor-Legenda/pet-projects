package edu.itmo.hh.pages;

import java.time.Duration;

import edu.itmo.hh.pages.results.HhVacancySearchResultsPage;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HhMainPage extends BasePage {

    private static final By SEARCH_INPUT = By.xpath(
            "//input[@data-qa='search-input']");

    private static final By HEADER_JOB_SEARCH = By.xpath(
            "//a[contains(@href,'/applicant') or contains(@href,'/vacancies')]"
                    + "[contains(normalize-space(.),'Ищу работу') or contains(normalize-space(.),'ваканс')]");

    private static final By SEARCH_BUTTON = By.xpath(
            "//button[@data-qa='search-button']");

    private static final By HIDE_BUTTON = By.xpath("//button[@data-qa='signup-modal-close']");

    public HhMainPage(WebDriver driver) {
        super(driver, STANDARD_TIMEOUT);
    }

    @DisplayName("Открыть главную и принять cookies при необходимости")
    public HhMainPage open() {
        driver.navigate().to(BASE_URL);
        acceptCookiesIfPresent();
        return this;
    }

    @DisplayName("Проверка: видна точка входа соискателя в шапке")
    public boolean isVisibleJobSeekerEntryPoint() {
        try {
            waitVisible(HEADER_JOB_SEARCH);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Поиск вакансий с главной по строке запроса")
    public HhVacancySearchResultsPage searchVacancies(String query) {
        var input = waitClickable(SEARCH_INPUT);
        input.click();
        input.clear();
        input.sendKeys(query);
        waitClickable(SEARCH_BUTTON).click();
        clickIfPresent(HIDE_BUTTON, Duration.ofSeconds(2));
        return new HhVacancySearchResultsPage(driver);
    }
}
