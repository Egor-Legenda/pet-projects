package edu.itmo.hh.pages.results;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import edu.itmo.hh.pages.BasePage;
import edu.itmo.hh.pages.HhVacancyAdvancedSearchPage;
import edu.itmo.hh.pages.HhVacancyPage;
import edu.itmo.hh.pages.enums.SerpEducation;
import edu.itmo.hh.pages.enums.SerpEmploymentForm;
import edu.itmo.hh.pages.enums.SerpWorkFormat;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HhVacancySearchResultsPage extends BasePage {

    public static final int AREA_MOSCOW = 1;
    public static final int AREA_SAINT_PETERSBURG = 2;

    private static final By VACANCY_CARD = By.xpath("//div[@data-qa='vacancy-serp__vacancy']");

    private static final By RESULTS_CONTAINER = By.xpath(
            "//div[@data-qa='vacancy-serp__results']"
                    + "|//main[contains(@class,'vacancy-serp-content')]");

    private static final By FIRST_VACANCY_TITLE = By.xpath(
            "(//a[@data-qa='serp-item__title'])[1]");

    private static final By SEARCH_TITLE_OR_HEADER = By.xpath(
            "//h1[@data-qa='title']"
                    + "|//div[@data-qa='title-container']"
                    + "|//div[@data-qa='vacancies-search-header']//h1");

    public HhVacancySearchResultsPage(WebDriver driver) {
        super(driver, STANDARD_TIMEOUT);
    }

    @DisplayName("Открыть выдачу по текстовому запросу")
    public static HhVacancySearchResultsPage openForText(WebDriver driver, String query) {
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        driver.navigate().to(BASE_URL + "/search/vacancy?text=" + encoded);
        var page = new HhVacancySearchResultsPage(driver);
        page.acceptCookiesIfPresent();
        return page;
    }

    @DisplayName("Проверка: есть блок результатов и заголовок поиска")
    public boolean isVisibleVacancyListOrHeader() {
        try {
            waitVisible(RESULTS_CONTAINER);
            waitVisible(SEARCH_TITLE_OR_HEADER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Прокрутка к левой колонке фильтров")
    public HhVacancySearchResultsPage scrollFilterSidebarIntoView() {
        WebElement aside = waitVisible(HhVacancySerpFilterLocators.SERP_CRITERIAS);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'nearest',inline:'nearest'});", aside);
        return this;
    }

    @DisplayName("Прокрутка элемента к центру области просмотра")
    private void scrollIntoViewCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center',inline:'nearest'});", element);
    }

    @DisplayName("Установить чекбокс через JS при необходимости")
    private void ensureCheckboxChecked(WebElement label, WebElement input) {
        if (input.isSelected()) {
            return;
        }
        scrollIntoViewCenter(label);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
    }

    @DisplayName("Клик по элементу через JavaScript")
    private void jsClick(WebElement element) {
        scrollIntoViewCenter(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @DisplayName("Выбор региона в боковой колонке по фрагменту подписи")
    public HhVacancySearchResultsPage selectRegionByLabelInSidebar(String regionLabelContains) {
        scrollFilterSidebarIntoView();
        WebElement label = waitVisible(HhVacancySerpFilterLocators.regionAreaFilterRowLabelContaining(regionLabelContains));
        WebElement input = label.findElement(By.xpath(".//input[@name='area' and @type='checkbox']"));
        int areaId = Integer.parseInt(input.getAttribute("value"));
        ensureCheckboxChecked(label, input);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains("area=" + areaId);
        });
        waitVisible(FIRST_VACANCY_TITLE);
        return this;
    }

    @DisplayName("Выбор опыта работы (radio) в боковой колонке")
    public HhVacancySearchResultsPage selectExperienceByRadioValueInSidebar(String radioValue) {
        scrollFilterSidebarIntoView();
        WebElement label = waitVisible(HhVacancySerpFilterLocators.experienceRowLabelByValue(radioValue));
        WebElement input = label.findElement(By.xpath(".//input[@type='radio']"));
        if (!input.isSelected()) {
            jsClick(input);
        }
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains(radioValue);
        });
        waitVisible(FIRST_VACANCY_TITLE);
        return this;
    }

    @DisplayName("Выбор формата работы в боковой колонке")
    public HhVacancySearchResultsPage selectWorkFormatByValueInSidebar(String workFormatValue) {
        scrollFilterSidebarIntoView();
        WebElement label = waitVisible(HhVacancySerpFilterLocators.workFormatRowLabelByValue(workFormatValue));
        WebElement input = label.findElement(By.xpath(".//input[@name='work_format' and @type='checkbox']"));
        ensureCheckboxChecked(label, input);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains(workFormatValue);
        });
        waitVisible(FIRST_VACANCY_TITLE);
        return this;
    }

    @DisplayName("Выбор формата работы в боковой колонке по enum")
    public HhVacancySearchResultsPage selectWorkFormatByValueInSidebar(SerpWorkFormat format) {
        return selectWorkFormatByValueInSidebar(format.urlValue());
    }

    @DisplayName("Выбор типа занятости в боковой колонке")
    public HhVacancySearchResultsPage selectEmploymentFormByValueInSidebar(String employmentFormValue) {
        scrollFilterSidebarIntoView();
        WebElement label = waitVisible(HhVacancySerpFilterLocators.employmentFormRowLabelByValue(employmentFormValue));
        jsClick(label);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains("employment_form=" + employmentFormValue);
        });
        waitVisible(FIRST_VACANCY_TITLE);
        return this;
    }

    @DisplayName("Выбор типа занятости в боковой колонке по enum")
    public HhVacancySearchResultsPage selectEmploymentFormByValueInSidebar(SerpEmploymentForm form) {
        return selectEmploymentFormByValueInSidebar(form.urlValue());
    }

    @DisplayName("Выбор образования в боковой колонке")
    public HhVacancySearchResultsPage selectEducationByValueInSidebar(String educationValue) {
        scrollFilterSidebarIntoView();
        By inpLoc = HhVacancySerpFilterLocators.educationInputInSidebar(educationValue);
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(inpLoc));
        jsClick(input);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains("education=" + educationValue);
        });
        waitVisible(FIRST_VACANCY_TITLE);
        return this;
    }

    @DisplayName("Выбор образования в боковой колонке по enum")
    public HhVacancySearchResultsPage selectEducationByValueInSidebar(SerpEducation education) {
        return selectEducationByValueInSidebar(education.urlValue());
    }

    @DisplayName("Открытие расширенного поиска по иконке в шапке выдачи")
    public HhVacancyAdvancedSearchPage openAdvancedSearchViaHeaderIcon() {
        By icon = By.xpath("//*[@data-qa='advanced-search']");
        WebElement el = waitClickable(icon);
        scrollIntoViewCenter(el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains("/search/vacancy/advanced");
        });
        return new HhVacancyAdvancedSearchPage(driver);
    }

    @DisplayName("Ввод минимальной зарплаты в боковой колонке")
    public HhVacancySearchResultsPage applyMinimumSalaryRubInSidebar(int salaryRub) {
        scrollFilterSidebarIntoView();
        WebElement input = waitVisible(HhVacancySerpFilterLocators.compensationMinInputInIncomeGroup());
        scrollIntoViewCenter(input);
        input.clear();
        input.sendKeys(String.valueOf(salaryRub));
        input.sendKeys(Keys.TAB);
        String needle = String.valueOf(salaryRub);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u != null && u.contains(needle);
        });
        waitVisible(FIRST_VACANCY_TITLE);
        return this;
    }

    @DisplayName("Открытие списка «Период выплат» в боковой колонке")
    public HhVacancySearchResultsPage openIncomePaymentPeriodSelectInSidebar() {
        scrollFilterSidebarIntoView();
        By trig = HhVacancySerpFilterLocators.incomePaymentPeriodSelectActivator();
        WebElement el = waitVisible(trig);
        scrollIntoViewCenter(el);
        el.click();
        wait.until(d -> "true".equals(d.findElement(trig).getAttribute("aria-expanded")));
        return this;
    }

    @DisplayName("Проверка: URL содержит выбранный регион (area)")
    public boolean isVisibleUrlContainsArea(int areaId) {
        String token = "area=" + areaId;
        try {
            return wait.until(d -> {
                String url = d.getCurrentUrl();
                return url != null && url.contains(token);
            });
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Проверка: текущий URL содержит все заданные подстроки без учёта регистра")
    public boolean isVisibleCurrentUrlContainsAllIgnoreCase(String... urlSubstrings) {
        try {
            return wait.until(d -> {
                String u = d.getCurrentUrl();
                if (u == null) {
                    return false;
                }
                String lower = u.toLowerCase(Locale.ROOT);
                for (String p : urlSubstrings) {
                    if (!lower.contains(p.toLowerCase(Locale.ROOT))) {
                        return false;
                    }
                }
                return true;
            });
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Проверка карточек выдачи: в каждой карточке есть все переданные фрагменты (без учёта регистра)")
    public boolean isVisibleVacancyCardsContainAllMarkers(
            int maxCards, String... mustContainInEveryCard) {
        try {
            wait.until(d -> !d.findElements(VACANCY_CARD).isEmpty());
            List<WebElement> cards = driver.findElements(VACANCY_CARD);
            if (cards.isEmpty()) {
                return false;
            }
            int n = Math.min(maxCards, cards.size());
            String[] lower = new String[mustContainInEveryCard.length];
            for (int m = 0; m < mustContainInEveryCard.length; m++) {
                lower[m] = mustContainInEveryCard[m].toLowerCase(Locale.ROOT);
            }
            for (int i = 0; i < n; i++) {
                List<WebElement> fresh = driver.findElements(VACANCY_CARD);
                if (i >= fresh.size()) {
                    break;
                }
                String text = fresh.get(i).getText().toLowerCase(Locale.ROOT);
                for (String needle : lower) {
                    if (!text.contains(needle)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @DisplayName("Текст заголовка первой вакансии в выдаче")
    public String readFirstVacancyTitleText() {
        return normalizeVacancyTitle(waitClickable(FIRST_VACANCY_TITLE).getText());
    }

    @DisplayName("Открытие первой вакансии из выдачи")
    public HhVacancyPage openFirstVacancy() {
        WebElement link = waitClickable(FIRST_VACANCY_TITLE);
        scrollIntoViewCenter(link);
        String href = link.getAttribute("href");
        if (href != null && href.contains("/vacancy/")) {
            String vacancyUrl = href.startsWith("http")
                    ? href
                    : BASE_URL + (href.startsWith("/") ? href : "/" + href);
            driver.navigate().to(vacancyUrl);
        } else {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        wait.until(ExpectedConditions.urlMatches(".*hh\\.ru/vacancy/\\d+.*"));
        return new HhVacancyPage(driver);
    }
}
