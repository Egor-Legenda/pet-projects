package edu.itmo.hh.search.filters.advanced;

import static edu.itmo.hh.pages.enums.SerpEducation.HIGHER;
import static edu.itmo.hh.pages.enums.SerpEmploymentForm.FULL;
import static edu.itmo.hh.pages.enums.SerpEmploymentForm.PART;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.itmo.hh.AbstractHhWebDriverTest;
import edu.itmo.hh.pages.results.HhVacancySearchResultsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

@Execution(ExecutionMode.SAME_THREAD)
@ResourceLock(value = "hh.ru", mode = ResourceAccessMode.READ)
class HhVacancyAdvancedFiltersE2ETest extends AbstractHhWebDriverTest {

    @DisplayName("Позитив. Кнопка расширенного поиска на выдаче -> открывается форма")
    @Test
    void fromSerp_headerIconOpensAdvancedForm() {
        var results = HhVacancySearchResultsPage.openForText(driver, "Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        var advanced = results.openAdvancedSearchViaHeaderIcon();
        assertTrue(advanced.isVisibleAdvancedFormLoaded());
    }

    @DisplayName("Позитив. Расширенный поиск: высшее и полная занятость -> параметры в URL выдачи")
    @Test
    void advancedForm_educationAndEmployment_submit_reflectedInSerpUrl() {
        var results = HhVacancySearchResultsPage.openForText(driver, "Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        var advanced = results.openAdvancedSearchViaHeaderIcon();
        assertTrue(advanced.isVisibleAdvancedFormLoaded());
        advanced.selectEducationByValue(HIGHER)
                .selectEmploymentFormByValue(FULL)
                .submitSearch();
        assertTrue(results.isVisibleVacancyListOrHeader());
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                        "text=java",
                        "education=" + HIGHER.urlValue(),
                        "employment_form=" + FULL.urlValue()));
    }

    @DisplayName("Позитив. Расширенный поиск с частичной занятостью -> в URL отражается тип занятости")
    @Test
    void advancedForm_partialEmployment_submit_reflectedInUrl() {
        var results = HhVacancySearchResultsPage.openForText(driver, "тестировщик");
        assertTrue(results.isVisibleVacancyListOrHeader());
        var advanced = results.openAdvancedSearchViaHeaderIcon();
        assertTrue(advanced.isVisibleAdvancedFormLoaded());
        advanced.selectEmploymentFormByValue(PART)
                .submitSearch();
        assertTrue(results.isVisibleVacancyListOrHeader());
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase("employment_form=" + PART.urlValue()));
    }
}
