package com.hepsiburada;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Her senaryodan önce Chrome'u açar, sonra kapatır. */
public class Driver {

    private static WebDriver driver;

    public static WebDriver get() {
        return driver;
    }

    @BeforeScenario
    public void openBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        // Bot tespitini azaltır: navigator.webdriver = true izini kapatır.
        // Bu olmadan Hepsiburada girişte "Beklenmeyen bir hata oluştu (N1E2)" veriyor.
        options.addArguments("--disable-blink-features=AutomationControlled");

        // "Chrome otomatik test yazılımı tarafından kontrol ediliyor" bilgisini ve otomasyon eklentisini kapatır
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Chrome'un "şifreyi kaydet" penceresini kapatır
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Not: PageLoadStrategy.EAGER kaldırıldı. Sayfanın güvenlik script'leri tam yüklenmeden
        // giriş yapılınca da aynı hata alınabiliyor; varsayılan (NORMAL) strateji tam yüklenmeyi bekler.

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterScenario
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
