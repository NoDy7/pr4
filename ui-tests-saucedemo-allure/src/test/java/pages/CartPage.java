package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private final By items = By.cssSelector("[data-test='inventory-item']");
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrices = By.cssSelector("[data-test='inventory-item-price']");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait until the cart page is opened")
    public void waitUntilOpened() {
        wait.until(ExpectedConditions.urlContains("/cart.html"));
        visible(checkoutButton);
    }

    public int getItemsCount() {
        return driver.findElements(items).size();
    }

    @Step("Read item names in the cart")
    public List<String> getItemNames() {
        return driver.findElements(itemNames).stream().map(e -> e.getText().trim()).toList();
    }

    @Step("Read item prices in the cart")
    public List<String> getItemPrices() {
        return driver.findElements(itemPrices).stream().map(e -> e.getText().trim()).toList();
    }

    @Step("Click Checkout")
    public void clickCheckout() {
        click(checkoutButton);
    }
}
