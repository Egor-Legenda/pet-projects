package edu.itmo.hh.search.filters.serp;

import static edu.itmo.hh.pages.results.HhVacancySearchResultsPage.AREA_MOSCOW;
import static edu.itmo.hh.pages.results.HhVacancySearchResultsPage.AREA_SAINT_PETERSBURG;


import static edu.itmo.hh.pages.enums.SerpEducation.HIGHER;
import static edu.itmo.hh.pages.enums.SerpEmploymentForm.FULL;
import static edu.itmo.hh.pages.enums.SerpEmploymentForm.PART;
import static edu.itmo.hh.pages.enums.SerpEmploymentForm.PROJECT;
import static edu.itmo.hh.pages.enums.SerpWorkFormat.HYBRID;
import static edu.itmo.hh.pages.enums.SerpWorkFormat.REMOTE;
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
class HhVacancySerpFiltersE2ETest extends AbstractHhWebDriverTest {

    private static final String EXPERIENCE_3_TO_6 = "between3And6";
    private static final String EXPERIENCE_1_TO_3 = "between1And3";
    private static final int MAX_CARDS = 10;

    private static final String SPB_CITY_FULL_NAME = "санкт-петербург";
    private static final String MSK_CITY_FULL_NAME = "москва";

    @DisplayName("Позитив. Боковые фильтры СПб, опыт 3–6 и гибрид -> URL и карточки отражают условия")
    @Test
    void spb_experienceHybrid_reflectedInUrlAndCards() {
        var results = HhVacancySearchResultsPage.openForText(driver, "Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        results.selectRegionByLabelInSidebar("Санкт-Петербург")
                .selectExperienceByRadioValueInSidebar(EXPERIENCE_3_TO_6)
                .selectWorkFormatByValueInSidebar(HYBRID);
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                        "area=" + AREA_SAINT_PETERSBURG,
                        EXPERIENCE_3_TO_6,
                        "hybrid"));
        assertTrue(results.isVisibleVacancyCardsContainAllMarkers(
                MAX_CARDS,
                "6 лет"));
    }

    @DisplayName("Позитив. Боковые фильтры СПб, полная занятость и высшее -> параметры в URL")
    @Test
    void spb_fullEmploymentHigherEducation_url() {
        var results = HhVacancySearchResultsPage.openForText(driver, "Python");
        assertTrue(results.isVisibleVacancyListOrHeader());
        results.selectRegionByLabelInSidebar("Санкт-Петербург")
                .selectEmploymentFormByValueInSidebar(FULL)
                .selectEducationByValueInSidebar(HIGHER);
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                        "area=" + AREA_SAINT_PETERSBURG,
                        "employment_form=" + FULL.urlValue(),
                        "education=" + HIGHER.urlValue()));
    }

    @DisplayName("Позитив. СПб, высшее образование, опыт 3–6 и гибрид -> URL и карточки отражают фильтры")
    @Test
    void spb_higherEducation_experienceHybrid_reflectedInUrlAndCards() {
        var results = HhVacancySearchResultsPage.openForText(driver, "Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        results.selectRegionByLabelInSidebar("Санкт-Петербург")
                .selectEducationByValueInSidebar(HIGHER)
                .selectExperienceByRadioValueInSidebar(EXPERIENCE_3_TO_6)
                .selectWorkFormatByValueInSidebar(HYBRID);
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                        "area=" + AREA_SAINT_PETERSBURG,
                        "education=" + HIGHER.urlValue(),
                        EXPERIENCE_3_TO_6,
                        "hybrid"));
        assertTrue(results.isVisibleVacancyCardsContainAllMarkers(
                MAX_CARDS,
                SPB_CITY_FULL_NAME));
    }

    @DisplayName("Позитив. Частичная занятость и опыт 1–3 -> параметры в URL")
    @Test
    void partTime_experience1to3_url() {
        var results = HhVacancySearchResultsPage.openForText(driver, "Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        results.selectEmploymentFormByValueInSidebar(PART)
                .selectExperienceByRadioValueInSidebar(EXPERIENCE_1_TO_3);
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                "employment_form=" + PART.urlValue(), EXPERIENCE_1_TO_3));
    }

    @DisplayName("Позитив. Подработка, высшее, удалённо, Москва -> URL и карточки отражают условия")
    @Test
    void project_higher_remote_moscow_cards() {
        var results = HhVacancySearchResultsPage.openForText(driver, "Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        results.selectRegionByLabelInSidebar("Москва")
                .selectEmploymentFormByValueInSidebar(PROJECT)
                .selectEducationByValueInSidebar(HIGHER)
                .selectWorkFormatByValueInSidebar(REMOTE);
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                        "area=" + AREA_MOSCOW,
                        "employment_form=" + PROJECT.urlValue(),
                        "education=" + HIGHER.urlValue(),
                        REMOTE.urlValue()));
        assertTrue(results.isVisibleVacancyCardsContainAllMarkers(
                MAX_CARDS,
                MSK_CITY_FULL_NAME));
    }

    @DisplayName(
            "Позитив. Блок дохода и графика в боковой колонке: мин. зарплата, рабочие часы, период ")
    @Test
    void sidebar_incomeAndScheduleFilters_combined() {
        int minRub = 120_000;
        var results = HhVacancySearchResultsPage.openForText(driver, "Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        results.applyMinimumSalaryRubInSidebar(minRub)
                .openIncomePaymentPeriodSelectInSidebar();
        assertTrue(results.isVisibleVacancyListOrHeader());
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                        String.valueOf(minRub),
                        "/search/vacancy",
                        "text=java"));
    }
}
