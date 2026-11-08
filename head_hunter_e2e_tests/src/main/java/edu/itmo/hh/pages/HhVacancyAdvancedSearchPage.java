package edu.itmo.hh.pages;

import edu.itmo.hh.pages.enums.SerpEducation;
import edu.itmo.hh.pages.enums.SerpEmploymentForm;
import edu.itmo.hh.pages.results.HhVacancySearchResultsPage;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HhVacancyAdvancedSearchPage extends BasePage {

    private static final By PAGE_HEADING = By.xpath("//h1[contains(.,'Поиск вакансий')]");
    private static final By SUBMIT_BUTTON = By.xpath("//button[contains(.,'Найти')]");

    public HhVacancyAdvancedSearchPage(WebDriver driver) {
        super(driver, STANDARD_TIMEOUT);
    }

    private static By inputNamed(String name, String value) {
        return By.xpath("//input[@name='" + name + "' and @value='" + value + "']");
    }

    @DisplayName("Проверка: форма расширенного поиска загружена")
    public boolean isVisibleAdvancedFormLoaded() {
        try {
            waitVisible(PAGE_HEADING);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Клик по элементу через JavaScript со скроллом к центру")
    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center',inline:'nearest'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @DisplayName("Выбор образования по значению как в URL")
    public HhVacancyAdvancedSearchPage selectEducationByValue(String educationValue) {
        By inp = inputNamed("education", educationValue);
        wait.until(ExpectedConditions.presenceOfElementLocated(inp));
        jsClick(driver.findElement(inp));
        return this;
    }

    @DisplayName("Выбор образования по enum")
    public HhVacancyAdvancedSearchPage selectEducationByValue(SerpEducation education) {
        return selectEducationByValue(education.urlValue());
    }

    @DisplayName("Выбор типа занятости по значению как в URL")
    public HhVacancyAdvancedSearchPage selectEmploymentFormByValue(String employmentFormValue) {
        By inp = inputNamed("employment_form", employmentFormValue);
        wait.until(ExpectedConditions.presenceOfElementLocated(inp));
        jsClick(driver.findElement(inp));
        return this;
    }

    @DisplayName("Выбор типа занятости по enum")
    public HhVacancyAdvancedSearchPage selectEmploymentFormByValue(SerpEmploymentForm form) {
        return selectEmploymentFormByValue(form.urlValue());
    }

    @DisplayName("Выбор опыта работы по значению как в URL")
    public HhVacancyAdvancedSearchPage selectExperienceByValue(String experienceValue) {
        By inp = inputNamed("experience", experienceValue);
        wait.until(ExpectedConditions.presenceOfElementLocated(inp));
        jsClick(driver.findElement(inp));
        return this;
    }

    @DisplayName("Отправка формы и возврат на выдачу")
    public HhVacancySearchResultsPage submitSearch() {
        WebElement btn = waitClickable(SUBMIT_BUTTON);
        jsClick(btn);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains("/search/vacancy?") && !u.contains("/advanced");
        });
        return new HhVacancySearchResultsPage(driver);
    }
}
