package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By productNames = By.cssSelector("li[data-sku] [class*='product_name'] a");

    public List<String> getProductNames() {
        find(productNames);
        return driver.findElements(productNames).stream().map(WebElement::getText).map(String::trim).toList();
    }
}
