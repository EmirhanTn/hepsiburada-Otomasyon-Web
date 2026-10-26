package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultPage extends BasePage {

    private final By resultTitle = By.cssSelector("[data-test-id='header-h1']");
    private final By products = By.cssSelector("li[id^='i'] article");

    public String getResultTitle() {
        return find(resultTitle).getText();
    }

    public List<WebElement> getProducts() {
        find(products);
        return driver.findElements(products).stream().filter(WebElement::isDisplayed).toList();
    }

    /**
     * Satırdaki ürün sayısı ekran genişliğine göre değiştiği için index yerine konuma bakıyoruz:
     * ilk ürünün altında kalan ilk ürün, ikinci satırın ilk ürünüdür.
     */
    public void clickFirstProductOfSecondRow() {
        List<WebElement> cards = getProducts();
        int firstRowY = cards.get(0).getLocation().getY();
        WebElement card = cards.stream()
                .filter(c -> c.getLocation().getY() > firstRowY + 10)
                .findFirst()
                .orElseThrow(() -> new AssertionError("İkinci satır bulunamadı"));

        click(card.findElement(By.tagName("a")));

        // Ürün yeni sekmede açılıyor, son açılan sekmeye geçiyoruz
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
        }
    }
}
