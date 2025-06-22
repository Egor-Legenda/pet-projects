package com.example.demo.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.example.demo.config.ConfigTest;
import com.example.demo.pages.CommunityPage;
import com.example.demo.pages.ProductPage;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.*;

public class ProductPageTest extends ConfigTest {

    @BeforeEach
    public void setUp() {
        open("https://vk.com/club225299895?w=product-225299895_10044406");
    }

    @Test
    @DisplayName("Проверка отображения изображения товара")
    public void shouldDisplayProductImage() {
        ProductPage.productImage.shouldBe(visible);
        ProductPage.productImage.shouldHave(Condition.attribute("src"));
        ProductPage.placeholderImage.shouldNotBe(visible);
    }

    @Test
    @DisplayName("Проверка отображения цены товара")
    public void shouldDisplayProductPrice() {
        ProductPage.productPrice.shouldBe(visible);
        ProductPage.productTitle.shouldNotBe(empty);
    }

    @Test
    @DisplayName("Проверка отображения описания товара")
    public void shouldDisplayProductDescription() {
        ProductPage.productDescription.shouldBe(visible);
        ProductPage.productDescription.shouldNotBe(empty);
    }

    @Test
    @DisplayName("Проверка отображения названия товара")
    public void shouldDisplayProductTitle() {
        ProductPage.productTitle.shouldBe(visible);
        ProductPage.productTitle.shouldNotBe(empty);
    }

    @Test
    @DisplayName("Проверка отображения вкладки 'Комментарии'")
    public void shouldDisplayCommentsTab() {
        ProductPage.commentsTab.shouldBe(visible);
        ProductPage.commentsTab.shouldHave(text("Комментарии"));
    }

    @Test
    @DisplayName("Проверка заглушки при отсутствии комментариев")
    public void shouldDisplayNoCommentsPlaceholder() {
        ProductPage.noCommentsPlaceholder.shouldBe(visible);
        ProductPage.noCommentsText.shouldHave(text("Нет комментариев"));
        ProductPage.commentsContainer.shouldNotBe(visible);
    }

    @Test
    @DisplayName("Проверка поля для ввода комментария")
    public void shouldDisplayCommentInput() {
        ProductPage.commentInput.shouldBe(visible);
        ProductPage.commentInput.shouldBe(enabled);
    }

    @Test
    @DisplayName("Проверка кнопки 'Добавить в желания'")
    public void shouldDisplayAddToDesiresButton() {
        ProductPage.actionMenu.shouldBe(visible);
        ProductPage.actionMenu.click();
        ProductPage.wishButton
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Добавить в желания"));
    }

    @Test
    @DisplayName("Проверка наличия кнопки 'Сохранить в закладкaх'")
    public void shouldDisplaySaveToBookmarksButton() {
        ProductPage.actionMenu.shouldBe(visible);
        ProductPage.actionMenu.click();
        ProductPage.bookmarks
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Сохранить в закладках"));
    }

    @Test
    @DisplayName("Проверка наличия кнопки 'Корзина'")
    public void shouldDisplayShopCartButton() {
        ProductPage.shopCartButton.shouldBe(visible);
    }

    @Test
    @DisplayName("Проверка перехода в магазин сообщества")
    public void shouldNavigateToCommunityShop() {
        String expectedUrl = "https://vk.com/market/product/fyvaf-225299895-10044406";
        ProductPage.communityLink.click();
        webdriver().shouldHave(url(expectedUrl));
    }

    @Test
    @DisplayName("Проверка перехода на основную страницу магазина")
    public void shouldNavigateToMainPage() {
        String expectedUrl = "https://vk.com/uslugi-225299895";
        ProductPage.hrefMainPage.shouldBe(visible).click();
        webdriver().shouldHave(url(expectedUrl));
        webdriver().shouldHave(urlContaining("uslugi-225299895"));
    }

    @Test
    @DisplayName("Проверка наличия и функциональности кнопки 'Написать' в магазине")
    public void shouldDisplayAndClickWriteButton() {
        ProductPage.writeStoreButton.shouldBe(visible)
                .shouldHave(text("Написать"));
        ProductPage.writeStoreButton.shouldBe(interactable);;
    }
    @Test
    @DisplayName("Проверка кнопки 'Поделиться' на странице товара")
    public void shouldDisplayAndClickShareButton() {
        ProductPage.shareButton
                .shouldBe(visible)
                .shouldHave(text("Поделиться"));

        ProductPage.shareButton.$("svg")
                .shouldBe(visible)
                .shouldHave(attribute("aria-hidden", "true"));

        ProductPage.shareButton
                .shouldBe(interactable);
    }
    @Test
    @DisplayName("Проверка кнопки 'Подписаться' на странице товара")
    public void shouldDisplayAndHandleSubscribeButton() {
        ProductPage.subscribeButton
                .shouldBe(visible)
                .shouldHave(text("Подписаться"));

        ProductPage.subscribeButton
                .shouldHave(cssClass("vkuiButton__modeOutline"))
                .shouldHave(cssClass("vkuiButton__appearanceNeutral"));

        ProductPage.subscribeButton
                .shouldBe(interactable);
    }

    @Test
    @DisplayName("Проверка кнопки 'Перейти в магазин'")
    public void shouldVerifyShopLinkButton() {
        String expectedUrl = "https://vk.com/uslugi-225299895";
        ProductPage.goShopLink
                .shouldBe(visible)
                .shouldHave(text("Перейти в магазин"));
        ProductPage.goShopLink.scrollIntoView("{block: 'center'}");

        executeJavaScript("arguments[0].click();", ProductPage.goShopLink);
        webdriver().shouldHave(numberOfWindows(2));
        switchTo().window(1);

        webdriver().shouldHave(url(expectedUrl));
        webdriver().shouldHave(urlContaining("uslugi-225299895"));

        closeWindow();
        switchTo().window(0);

    }
}
