package edu.itmo.hh.e2e.main;

import static edu.itmo.hh.pages.HhVacancyPage.BODY_SNIPPET_EMPLOYMENT;
import static edu.itmo.hh.pages.HhVacancyPage.BODY_SNIPPET_EXPERIENCE;
import static edu.itmo.hh.pages.HhVacancyPage.BODY_SNIPPET_SCHEDULE;
import static edu.itmo.hh.pages.results.HhVacancySearchResultsPage.AREA_SAINT_PETERSBURG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.itmo.hh.AbstractHhWebDriverTest;
import edu.itmo.hh.pages.HhMainPage;
import edu.itmo.hh.pages.HhVacancyPage;
import edu.itmo.hh.pages.enums.SerpEducation;
import edu.itmo.hh.pages.enums.SerpEmploymentForm;
import edu.itmo.hh.pages.enums.SerpWorkFormat;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

@Execution(ExecutionMode.SAME_THREAD)
@ResourceLock(value = "hh.ru", mode = ResourceAccessMode.READ)
class HhVacancyMainE2ETest extends AbstractHhWebDriverTest {

    private static final int MAX_CARDS_TO_CHECK = 12;
    private static final String QUERY = "Java";
    private static final String EXPERIENCE_3_TO_6 = "between3And6";

    private static final String SPB_CITY_FULL_NAME = "Санкт-Петербург";

    @DisplayName("Позитив. Расширенный поиск по СПб: образование, занятость, опыт, на месте работодателя -> выдача и карточки соответствуют фильтрам, переход к регистрации или форме отклика")
    @Test
    void mainFlow_withAdvancedForm_spbEducationEmploymentExperience() {
        var mainPage = new HhMainPage(driver).open();
        assertTrue(mainPage.isVisibleJobSeekerEntryPoint());

        var advancedPage = mainPage
                .searchVacancies(QUERY)
                .selectRegionByLabelInSidebar(SPB_CITY_FULL_NAME)
                .openAdvancedSearchViaHeaderIcon();
        assertTrue(advancedPage.isVisibleAdvancedFormLoaded());

        var results = advancedPage
                .selectEducationByValue(SerpEducation.HIGHER)
                .selectEmploymentFormByValue(SerpEmploymentForm.FULL)
                .selectExperienceByValue(EXPERIENCE_3_TO_6)
                .submitSearch()
                .selectWorkFormatByValueInSidebar(SerpWorkFormat.ON_SITE);
        assertTrue(results.isVisibleVacancyListOrHeader());
        assertTrue(results.isVisibleUrlContainsArea(AREA_SAINT_PETERSBURG));
        assertTrue(results.isVisibleCurrentUrlContainsAllIgnoreCase(
                        "text=" + QUERY.toLowerCase(Locale.ROOT),
                        "education=" + SerpEducation.HIGHER.urlValue(),
                        "employment_form=" + SerpEmploymentForm.FULL.urlValue(),
                        EXPERIENCE_3_TO_6,
                        SerpWorkFormat.ON_SITE.urlValue()));
        assertTrue(results.isVisibleVacancyCardsContainAllMarkers(
                MAX_CARDS_TO_CHECK,
                "опыт 3-6 лет"));
        
        String titleOnSerp = results.readFirstVacancyTitleText();
        var vacancy = results.openFirstVacancy();
        assertTrue(vacancy.isVisibleVacancyTitle());
        assertEquals(titleOnSerp, vacancy.readVacancyTitleText(), "Заголовок на странице вакансии как в выдаче");
        assertTrue(vacancy.isVisibleVacancyBodyContainsAllIgnoreCase(
                QUERY,
                BODY_SNIPPET_SCHEDULE,
                BODY_SNIPPET_EMPLOYMENT,
                BODY_SNIPPET_EXPERIENCE,
                "опыт 3-6 лет",
                SerpEmploymentForm.FULL.sidebarLabelFragment(),
                SerpEducation.HIGHER.sidebarLabelFragment()));


        vacancy.clickRespondAndAssertNavigationToResponseOrLogin();
        assertTrue(HhVacancyPage.isVisibleLooksLikeRespondOrAuthUrl(driver.getCurrentUrl()));
    }

    @DisplayName("Позитив. Главная, поиск, фильтр СПб, карточка и отклик -> форма отклика или регистрации")
    @Test
    void mainFlow_serpFiltersOnly_openVacancy_respond() {
        var mainPage = new HhMainPage(driver).open();
        assertTrue(mainPage.isVisibleJobSeekerEntryPoint());
        var results = mainPage
                .searchVacancies(QUERY)
                .selectRegionByLabelInSidebar(SPB_CITY_FULL_NAME)
                .selectWorkFormatByValueInSidebar(SerpWorkFormat.HYBRID);
        assertTrue(results.isVisibleVacancyListOrHeader());
        assertTrue(results.isVisibleUrlContainsArea(AREA_SAINT_PETERSBURG));
        assertTrue(results.isVisibleVacancyCardsContainAllMarkers(
                MAX_CARDS_TO_CHECK,
                SPB_CITY_FULL_NAME));

        String titleOnSerp = results.readFirstVacancyTitleText();
        var vacancy = results.openFirstVacancy();
        assertTrue(vacancy.isVisibleVacancyTitle());

        assertEquals(titleOnSerp, vacancy.readVacancyTitleText(), "Заголовок на странице вакансии как в выдаче");
        assertTrue(vacancy.isVisibleVacancyBodyContainsAllIgnoreCase(
                QUERY,
                SPB_CITY_FULL_NAME,
                BODY_SNIPPET_SCHEDULE,
                BODY_SNIPPET_EMPLOYMENT,
                SerpWorkFormat.HYBRID.shortLabelRu()));

        vacancy.clickRespondAndAssertNavigationToResponseOrLogin();

        assertTrue(HhVacancyPage.isVisibleLooksLikeRespondOrAuthUrl(driver.getCurrentUrl()));
    }
}
