package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class HomePage extends BasePage {

    private final By accountMenu = By.id("myAccount");
    private final By accountTitle = By.cssSelector("#myAccount [data-test-id='account']");
    private final By loginLink = By.id("login");
    private final By searchInput = By.cssSelector("input[data-test-id='search-bar-input']");

    public void open() {
        driver.get("https://www.hepsiburada.com");
        find(searchInput);
    }

    /**
     * Sağ üstteki "Giriş Yap" menüsü mouse ile üzerine gelince açılır.
     * Sayfa tam yüklenmeden gelinirse menü açılmıyor; bu yüzden mouse'u çekip tekrar getirerek açılana kadar deniyoruz.
     */
    public void clickLogin() {
        wait.until(d -> {
            new Actions(d)
                    .moveToElement(d.findElement(searchInput))
                    .moveToElement(d.findElement(accountMenu))
                    .pause(Duration.ofMillis(500))
                    .perform();
            return isDisplayed(loginLink);
        });
        click(loginLink);
    }

    /** Giriş yapılınca hesap alanının title değeri "Giriş Yap" yerine "Hesabım" olur. */
    public boolean isLoggedIn() {
        try {
            return wait.until(d -> "Hesabım".equals(d.findElement(accountTitle).getDomAttribute("title")));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getUserName() {
        return find(accountTitle).getText().replace("Hesabım", "").trim();
    }

    public void search(String text) {
        click(searchInput);
        // Tıklayınca arama kutusu yenisiyle değişiyor, bu yüzden odaktaki kutuya yazıyoruz
        WebElement activeInput = wait.until(d -> {
            WebElement active = d.switchTo().activeElement();
            return "search-bar-input".equals(active.getDomAttribute("data-test-id")) ? active : null;
        });
        activeInput.sendKeys(text + Keys.ENTER);
    }
}
