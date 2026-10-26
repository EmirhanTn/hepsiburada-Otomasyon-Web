package com.hepsiburada.pages;

import org.openqa.selenium.By;

import java.util.concurrent.ThreadLocalRandom;

public class LoginPage extends BasePage {

    private final By emailInput = By.id("txtUserName");
    private final By passwordInput = By.id("txtPassword");
    private final By loginButton = By.id("btnLogin");

    public void login(String email, String password) {
        typeLikeHuman(emailInput, email);
        pause(400, 800);
        // Bazen önce sadece e-posta istenir, şifre alanı "Giriş yap"a basınca açılır
        if (!isDisplayed(passwordInput)) {
            click(loginButton);
        }
        typeLikeHuman(passwordInput, password);
        pause(400, 800);
        // Butona basmadan önce kısa bir duraksama (insan gibi davranmak için)
        pause(500, 1200);
        click(loginButton);
    }

    /** minMs ile maxMs arasında rastgele bekler. */
    private static void pause(long minMs, long maxMs) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(minMs, maxMs + 1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
