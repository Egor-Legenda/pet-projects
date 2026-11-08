package edu.itmo.hh.pages;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected static final String BASE_URL = "https://hh.ru";

    protected static final Duration STANDARD_TIMEOUT = Duration.ofSeconds(10);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    @DisplayName("Ожидание видимости элемента по локатору")
    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @DisplayName("Ожидание кликабельности элемента по локатору")
    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @DisplayName("Принять cookies / закрыть информер при появлении")
    protected void acceptCookiesIfPresent() {
        By accept = By.xpath(
                "//button[contains(normalize-space(.),'Понятно')]");
        try {
            WebElement btn = driver.findElement(accept);
            btn.click();
        } catch (Exception e) {}

    }

    @DisplayName("Клик по элементу, если он появился в течение таймаута")
    protected void clickIfPresent(By locator, Duration timeout) {
        try {
            var shortWait = new WebDriverWait(driver, timeout);
            shortWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (TimeoutException ignored) {
        }
    }

    @DisplayName("Нормализация текста заголовка вакансии")
    protected static String normalizeVacancyTitle(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.trim().replaceAll("\\s+", " ");
    }
}
