package com.example.demo.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class CommunityPage {

    public static SelenideElement
            communityName = $("h1[class*='page_name']"),
            subscribeButton = $("button[class*='FlatButton']"),
            productsSectionButton = $x("//span[contains(@class, 'ui_tab_content_new') and text()='Товары']"),
            productsTab = $("li[data-testid='group_tab_market']"),
            productsPage = $("a[data-testid='market_services_tab_show_all_button']"),
            productsHeader = $("h2[class*='market_header']"),
            descriptions = $("div[class*='group_info_row']"),
            dateCreation  = $("div[id*='react_rootGroups_render_name_history']"),
            communityInfo = $("a[class*='groups-redesigned-info-more']");
}
