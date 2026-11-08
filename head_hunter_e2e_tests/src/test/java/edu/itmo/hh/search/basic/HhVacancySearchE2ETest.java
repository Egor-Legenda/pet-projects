package edu.itmo.hh.search.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.itmo.hh.AbstractHhWebDriverTest;
import edu.itmo.hh.pages.HhMainPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

@Execution(ExecutionMode.SAME_THREAD)
@ResourceLock(value = "hh.ru", mode = ResourceAccessMode.READ)
class HhVacancySearchE2ETest extends AbstractHhWebDriverTest {

    @DisplayName("Позитив. Поиск с главной по запросу -> первая карточка с тем же заголовком")
    @Test
    void searchFromMain_opensFirstVacancyCard() {
        var results = new HhMainPage(driver)
                .open()
                .searchVacancies("Java");
        assertTrue(results.isVisibleVacancyListOrHeader());
        var titleInList = results.readFirstVacancyTitleText();

        var vacancy = results.openFirstVacancy();
        assertTrue(vacancy.isVisibleVacancyTitle());

        assertEquals(
                titleInList,
                vacancy.readVacancyTitleText(),
                "Заголовок на карточке должен совпадать с выбранной строкой в выдаче");
    }
}
