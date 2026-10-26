package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {

    private final By productTitle = By.cssSelector("h1[data-test-id='title']");
    private final By addToCartButton = By.cssSelector("button[data-test-id='addToCart']");
    private final By addedMessage = By.xpath("//*[contains(text(),'Ürün sepetinizde') or contains(text(),'Ürün sepete eklendi')]");
    private final By continueShoppingButton = By.xpath("//button[normalize-space()='Alışverişe devam et']");
    private final By cartIcon = By.cssSelector("a[href*='/sepetim']");

    public String getTitle() {
        return find(productTitle).getText().trim();
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public String getAddedMessage() {
        WebElement message = wait.until(d -> d.findElements(addedMessage).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));
        return message.getText();
    }

    /** Açılan "Ürün sepetinizde" penceresi kapatılır, sonra header'daki sepet ikonuna tıklanır. */
    public void openCart() {
        if (isDisplayed(continueShoppingButton)) {
            click(continueShoppingButton);
        }
        click(cartIcon);
    }
}
