package wiki.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class SearchResultsPage {

    private final SelenideElement pageTitle = $(".firstHeading");
    private final SelenideElement searchResults = $(".searchresults");


    public SelenideElement getPageTitle() {
        return pageTitle;
    }

    public SelenideElement getSearchResults() {
        return searchResults;
    }

}
