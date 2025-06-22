package com.example.demo.tests;

import com.example.demo.config.ConfigTest;
import com.example.demo.pages.CommunityPage;
import com.example.demo.pages.ProductPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

public class CommunityPageTest extends ConfigTest {

    @BeforeEach
    public void setUp() {

        open("https://vk.com/club225299895");
    }

    @Test
    @DisplayName("Проверка отображения названия сообщества")
    public void shouldDisplayCommunityName() {
        CommunityPage.communityName.shouldBe(visible);
        CommunityPage.communityName.shouldHave(text("Test public for test"));
    }

    @Test
    @DisplayName("Проверка отображения кнопки 'Подписаться'")
    public void shouldDisplaySubscribeButton() {
        CommunityPage.subscribeButton.shouldBe(visible);
        CommunityPage.subscribeButton.shouldBe(enabled);
    }

    @Test
    @DisplayName("Проверка отображения вкладки 'Товары'")
    public void shouldDisplayProductsTab() {
        CommunityPage.productsTab.shouldBe(visible);
        CommunityPage.productsTab.shouldHave(text("Товары"));
    }

    @Test
    @DisplayName("Проверка отображения описания о сообществе")
    public void shouldDisplayCommunityInfo() {
        CommunityPage.communityInfo.shouldBe(visible).click();
        CommunityPage.descriptions.shouldNotBe(empty);
    }

    @Test
    @DisplayName("Проверка отображения информации о дате создания сообществе")
    public void shouldDisplayDateCreation() {
        CommunityPage.communityInfo.shouldBe(visible).click();
        CommunityPage.dateCreation.shouldNotBe(empty);
    }
}
