package edu.itmo.hh.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HhVacancyPage extends BasePage {

    public static final String BODY_SNIPPET_SCHEDULE = "график";
    public static final String BODY_SNIPPET_EMPLOYMENT = "занятость";
    public static final String BODY_SNIPPET_EXPERIENCE = "опыт";

    private static final String[] RESPOND_OR_AUTH_URL_MARKERS = {
        "vacancy_response",
        "account/login",
        "account/signup",
        "/auth",
        "logon",
        "passport",
    };

    private static final By VACANCY_TITLE = By.xpath("//*[@data-qa='vacancy-title']");

    private static final By RESPOND_CONTROL = By.xpath(
            "//*[self::a or self::button][contains(normalize-space(.),'Откликнуться')]"
                    + "|//*[@data-qa='vacancy-response-link-top']");

    private static final By RESPONSE_DIALOG_OR_MODAL =
            By.xpath("//*[@role='dialog']|//*[contains(@class,'magritte-modal')]");

    private static final By BODY = By.xpath("//body");

    public HhVacancyPage(WebDriver driver) {
        super(driver, STANDARD_TIMEOUT);
    }

    private static boolean urlLooksLikeRespondOrAuth(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        String lower = url.toLowerCase(Locale.ROOT);
        for (String marker : RESPOND_OR_AUTH_URL_MARKERS) {
            if (lower.contains(marker)) {
                boolean signup = lower.contains("account/signup");
                boolean backurlOk = lower.contains("backurl=");
                return !signup || backurlOk;
            }
        }
        return false;
    }

    @DisplayName("Проверка: URL похож на отклик, кабинет или вход/регистрацию")
    public static boolean isVisibleLooksLikeRespondOrAuthUrl(String url) {
        return urlLooksLikeRespondOrAuth(url);
    }

    @DisplayName("Проверка: заголовок вакансии отображается")
    public boolean isVisibleVacancyTitle() {
        try {
            waitVisible(VACANCY_TITLE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Текст заголовка вакансии")
    public String readVacancyTitleText() {
        return normalizeVacancyTitle(waitVisible(VACANCY_TITLE).getText());
    }

    private String vacancyBodyTextLowerCase() {
        waitVisible(VACANCY_TITLE);
        return driver.findElement(BODY).getText().toLowerCase(Locale.ROOT);
    }

    @DisplayName("Проверка в тексте страницы есть все перечисленные фрагменты (без учёта регистра)")
    public boolean isVisibleVacancyBodyContainsAllIgnoreCase(String... textFragments) {
        try {
            String body = vacancyBodyTextLowerCase();
            List<String> missing = new ArrayList<>();
            for (String fragment : textFragments) {
                if (fragment == null || fragment.isEmpty()) {
                    continue;
                }
                if (!body.contains(fragment.toLowerCase(Locale.ROOT))) {
                    missing.add(fragment);
                }
            }
            return missing.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Откликнуться и дождаться перехода к отклику, входу или регистрации")
    public HhVacancyPage clickRespondAndAssertNavigationToResponseOrLogin() {
        waitVisible(VACANCY_TITLE);
        int windowsBefore = driver.getWindowHandles().size();
        WebElement control = wait.until(d -> d.findElement(RESPOND_CONTROL));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center',inline:'nearest'});", control);
        String href = control.getAttribute("href");
        if (href != null && !href.isEmpty() && !href.startsWith("javascript:")) {
            String full = href.startsWith("http") ? href : BASE_URL + (href.startsWith("/") ? href : "/" + href);
            if (urlLooksLikeRespondOrAuth(full)) {
                driver.navigate().to(full);
            } else {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", control);
            }
        } else {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", control);
        }
        wait.until(d -> {
            if (d.getWindowHandles().size() > windowsBefore) {
                return true;
            }
            String u = d.getCurrentUrl();
            if (urlLooksLikeRespondOrAuth(u)) {
                return true;
            }
            for (WebElement el : d.findElements(RESPONSE_DIALOG_OR_MODAL)) {
                try {
                    if (el.isDisplayed()) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
            return false;
        });
        if (driver.getWindowHandles().size() > windowsBefore) {
            String last = null;
            for (String h : driver.getWindowHandles()) {
                last = h;
            }
            if (last != null) {
                driver.switchTo().window(last);
            }
        }
        return this;
    }
}
