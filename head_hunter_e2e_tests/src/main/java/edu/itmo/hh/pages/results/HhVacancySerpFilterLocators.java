package edu.itmo.hh.pages.results;

import edu.itmo.hh.pages.enums.SerpEmploymentForm;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;

public final class HhVacancySerpFilterLocators {

    private HhVacancySerpFilterLocators() {
    }

    public static final By SERP_CRITERIAS = By.xpath("//aside[@data-qa='serp__criterias']");

    private static final String ASIDE = "//aside[@data-qa='serp__criterias']";

    private static String nova(String headingFragment) {
        return ASIDE + "//*[@data-qa='serp__novafilter-group'][.//h2[contains(.,'" + headingFragment + "')]]";
    }

    private static final String REGION_ROW_LABEL = nova("Регион") + "//label[@data-qa='cell']";
    private static final String EMPLOYMENT_ROW_LABEL = nova("Тип занятости") + "//label[@data-qa='cell']";

    @DisplayName("Строка фильтра региона по фрагменту подписи")
    public static By regionAreaFilterRowLabelContaining(String text) {
        String lit = xq(text);
        return By.xpath(REGION_ROW_LABEL + "[.//*[@data-qa='cell-text-content'][contains(.," + lit + ")]]");
    }

    @DisplayName("Поле минимальной зарплаты в блоке «Уровень дохода»")
    public static By compensationMinInputInIncomeGroup() {
        return By.xpath(nova("Уровень дохода") + "//input[@data-qa='novafilters-custom-compensation']");
    }

    @DisplayName("Триггер «Период выплат» в блоке дохода")
    public static By incomePaymentPeriodSelectActivator() {
        return By.xpath(nova("Уровень дохода") + "//*[@data-qa='magritte-select-activator'][contains(.,'Период выплат')]");
    }

    @DisplayName("Строка опыта работы по значению radio")
    public static By experienceRowLabelByValue(String radioValue) {
        return By.xpath(
                nova("Опыт работы") + "//label[@data-qa='cell'][.//input[@type='radio' and @value='" + radioValue + "']]");
    }

    @DisplayName("Строка формата работы по значению чекбокса")
    public static By workFormatRowLabelByValue(String workFormatValue) {
        return By.xpath(
                nova("Формат работы")
                        + "//label[@data-qa='cell'][.//input[@name='work_format' and @value='"
                        + workFormatValue
                        + "']]");
    }

    @DisplayName("Строка типа занятости по значению URL")
    public static By employmentFormRowLabelByValue(String employmentFormValue) {
        String lit = xq(employmentSidebarLabelFragment(employmentFormValue));
        return By.xpath(EMPLOYMENT_ROW_LABEL + "[.//*[@data-qa='cell-text-content'][contains(.," + lit + ")]]");
    }

    @DisplayName("Input образования в боковой колонке")
    public static By educationInputInSidebar(String educationValue) {
        return By.xpath(nova("Образование") + "//input[@name='education' and @value='" + educationValue + "']");
    }

    private static String employmentSidebarLabelFragment(String employmentFormValue) {
        SerpEmploymentForm e = SerpEmploymentForm.fromUrlValue(employmentFormValue);
        return e != null ? e.sidebarLabelFragment() : employmentFormValue;
    }

    private static String xq(String s) {
        if (s == null || s.isEmpty()) {
            return "''";
        }
        return s.indexOf('\'') < 0 ? "'" + s + "'" : "\"" + s + "\"";
    }
}
