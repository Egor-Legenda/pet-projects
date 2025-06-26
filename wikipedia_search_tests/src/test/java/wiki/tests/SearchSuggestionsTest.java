package wiki.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Поиск на Википедии")
@Feature("Саджесты поиска")
public class SearchSuggestionsTest extends BaseTest {

    @Test
    @Story("Форматирование саджестов")
    @DisplayName("Проверка форматирования саджестов: начало с запроса и визуальное выделение")
    @Description("""
            Тест проверяет два ключевых аспекта саджестов:
            1. Что каждый саджест начинается с введенного поискового запроса
            2. Что начало саджеста (совпадающее с запросом) имеет визуальное выделение
               (жирный шрифт или альтернативное выделение цветом/фоном)""")
    @Severity(SeverityLevel.NORMAL)
    public void suggestionsStartWithQuery() {
        String query = "Igo";

        Allure.step("Вводим поисковый запрос '" + query + "'", () -> {
            mainPage.enterSearchQuery(query);
        });

        Allure.step("Проверяем каждый саджест в результатах", () -> {
            mainPage.getSuggestionResults().forEach(firstSuggestion -> {
                SelenideElement highlight = firstSuggestion.$(".suggestion-highlight");

                Allure.step("Проверяем, что саджест начинается с запроса и выделен", () -> {
                    highlight.shouldBe(visible)
                            .shouldHave(text(query));

                    String fontWeight = highlight.getCssValue("font-weight");

                    if (!List.of("700", "bold", "bolder").contains(fontWeight)) {
                        Allure.addAttachment("Визуальное выделение",
                                "Текст не выделен жирным шрифтом, проверяем альтернативное выделение");

                        String color = highlight.getCssValue("color");
                        String backgroundColor = highlight.getCssValue("background-color");

                        assertThat(color).isNotEqualTo(backgroundColor)
                                .as("Текст должен быть визуально выделен (цветом или фоном)");
                    }
                });
            });
        });
    }
}
