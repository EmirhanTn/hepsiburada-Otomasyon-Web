package com.hepsiburada.pages;

import com.hepsiburada.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/** Tüm sayfaların kullandığı ortak bekleme / tıklama / yazma metotları. */
public class BasePage {

    protected WebDriver driver = Driver.get();
    // Sayfa kendini yeniden çizince elementler "bayatlayabiliyor", beklerken bu hatayı yok sayıp tekrar deniyoruz
    protected FluentWait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20))
            .ignoring(StaleElementReferenceException.class);

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        click(wait.until(ExpectedConditions.elementToBeClickable(locator)));
    }

    protected void click(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Üstünde başka bir katman (banner, popup vb.) varsa JavaScript ile tıklıyoruz
            js.executeScript("arguments[0].click();", element);
        }
    }

    protected void type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    /** Harfleri tek tek, her harften sonra 80-250 ms arası rastgele bekleyerek yazar. */
    protected void typeLikeHuman(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        Actions actions = new Actions(driver).click(element);
        for (char letter : text.toCharArray()) {
            actions.sendKeys(String.valueOf(letter))
                    .pause(Duration.ofMillis(ThreadLocalRandom.current().nextInt(80, 250)));
        }
        actions.perform();
    }

    protected boolean isDisplayed(By locator) {
        return driver.findElements(locator).stream().anyMatch(WebElement::isDisplayed);
    }
}
