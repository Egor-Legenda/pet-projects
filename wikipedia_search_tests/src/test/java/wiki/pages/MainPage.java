package wiki.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private final SelenideElement searchInput = $("#searchInput");
    private final SelenideElement searchButton = $(".pure-button");
    private final List<SelenideElement> suggestionResults = $$(".suggestions-dropdown a");

    public void enterSearchQuery(String query) {
        searchInput.setValue(query);
    }

    public List<SelenideElement> getSuggestionResults() {
        return suggestionResults;
    }

    public void clickSuggestion(int index) {
        suggestionResults.get(index).click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }


    public SelenideElement getSearchInput() {
        return searchInput;
    }

}