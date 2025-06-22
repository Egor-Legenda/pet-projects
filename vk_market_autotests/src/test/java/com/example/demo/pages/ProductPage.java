package com.example.demo.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {

    public static SelenideElement
            productTitle = $("h1[data-testid='market_item_page_title']"),
            productPrice = $("h2[data-testid='market_item_page_price']"),
            productDescription = $("div[data-testid='market_item_page_description']"),
            productImage = $("img[data-testid='product_image']"),
            placeholderImage = $("div[data-testid='placeholder_description']"),
            reportButton = $("button[data-testid='report_button']"),
            quantitySelector = $("div[data-testid='quantity_selector']"),
            favoriteButton = $("button[data-testid='favorite_button']"),
            commentsTab = $("div[data-testid='tabs-item-comments']"),
            noCommentsPlaceholder = $("div[data-testid='market_item_no_comments']"),
            noCommentsText = $("div[data-testid='market_item_no_comments'] [data-testid='placeholder_description']"),
            commentsContainer = $("div[data-testid='market_comments_list']"),
            commentInput = $("div[data-testid='comment_input']"),
            addToCartButton = $("button[data-testid='add_to_cart_button']"),
            actionMenu = $("button[data-testid='market_item_page_actions_opener_button_not_checked']"),
            wishButton = $("div[data-testid='dropdownactionsheet-item-wishlist']"),
            hrefMainPage = $("a[data-testid='breadcrumb']"),
            shopCartButton = $("button[data-testid='market_item_page_storefront_cart_button']"),
            bookmarks = $("svg[data-testid='dropdownactionsheet-item-bookmark']"),
            writeStoreButton =  $("button[data-testid='market_item_page_primary_button']"),
            shareButton = $("button[data-testid='market_item_page_share']"),
            subscribeButton = $("button[data-testid='market_item_page_subscribe_button']"),
            goShopLink = $("a[data-testid='market_item_page_shop_link']"),
            communityLink = $("a[href*='/club225299895']");

}
