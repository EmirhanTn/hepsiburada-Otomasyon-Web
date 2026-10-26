package com.hepsiburada.steps;

import com.hepsiburada.pages.CartPage;
import com.hepsiburada.pages.HomePage;
import com.hepsiburada.pages.LoginPage;
import com.hepsiburada.pages.ProductPage;
import com.hepsiburada.pages.SearchResultPage;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class StepImplementation {

    private String productName;

    @Step("Hepsiburada ana sayfası açılır")
    public void openHomePage() {
        new HomePage().open();
    }

    @Step("Giriş Yap butonuna tıklanır")
    public void clickLogin() {
        new HomePage().clickLogin();
    }

    @Step("Kullanıcı bilgileri ile giriş yapılır")
    public void login() {
        // Bilgiler env/default/user.properties dosyasından okunur
        String email = System.getenv("HB_EMAIL");
        String password = System.getenv("HB_PASSWORD");
        assertThat(email).as("env/default/user.properties dosyasına HB_EMAIL yazın").isNotBlank();
        assertThat(password).as("env/default/user.properties dosyasına HB_PASSWORD yazın").isNotBlank();

        new LoginPage().login(email, password);
    }

    @Step("Giriş yapıldığı doğrulanır")
    public void verifyLogin() {
        HomePage homePage = new HomePage();
        assertThat(homePage.isLoggedIn()).as("Kullanıcı giriş yapamadı").isTrue();
        Gauge.writeMessage("Giriş yapan kullanıcı: " + homePage.getUserName());
    }

    @Step("<text> araması yapılır")
    public void search(String text) {
        new HomePage().search(text);
    }

    @Step("Arama sonuçlarının <text> için geldiği doğrulanır")
    public void verifySearchResults(String text) {
        SearchResultPage resultPage = new SearchResultPage();
        assertThat(resultPage.getResultTitle()).containsIgnoringCase(text);
        assertThat(resultPage.getProducts()).as("Ürün listesi boş").isNotEmpty();
    }

    @Step("İkinci satırdaki ilk ürüne tıklanır")
    public void clickFirstProductOfSecondRow() {
        new SearchResultPage().clickFirstProductOfSecondRow();
    }

    @Step("Ürün sayfasının açıldığı doğrulanır")
    public void verifyProductPage() {
        productName = new ProductPage().getTitle();
        assertThat(productName).isNotBlank();
        Gauge.writeMessage("Seçilen ürün: " + productName);
    }

    @Step("Ürün sepete eklenir")
    public void addToCart() {
        new ProductPage().addToCart();
    }

    @Step("Sepete eklendi mesajı görülür")
    public void verifyAddedMessage() {
        assertThat(new ProductPage().getAddedMessage()).isNotBlank();
    }

    @Step("Sepet açılır")
    public void openCart() {
        new ProductPage().openCart();
    }

    @Step("Ürünün sepette olduğu doğrulanır")
    public void verifyProductInCart() {
        assertThat(new CartPage().getProductNames()).as("Ürün sepette yok: " + productName).contains(productName);
    }
}
