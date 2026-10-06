package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    private final By title = By.cssSelector("[data-test='title']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By sortSelect = By.cssSelector("[data-test='product-sort-container']");
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrices = By.cssSelector("[data-test='inventory-item-price']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait until the inventory page is opened")
    public void waitUntilOpened() {
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        visible(title);
    }

    @Step("Read the page title")
    public String getTitle() {
        return text(title);
    }

    /** slug example: "sauce-labs-backpack" */
    @Step("Add '{slug}' to the cart")
    public void addToCart(String slug) {
        click(By.id("add-to-cart-" + slug));
    }

    @Step("Remove '{slug}' from the cart")
    public void removeFromCart(String slug) {
        click(By.id("remove-" + slug));
    }

    /** Returns 0 when the badge is not rendered (empty cart). */
    public int getCartCount() {
        List<WebElement> badge = driver.findElements(cartBadge);
        return badge.isEmpty() ? 0 : Integer.parseInt(badge.get(0).getText().trim());
    }

    @Step("Wait for the cart badge to show {expected}")
    public void waitForCartCount(int expected) {
        if (expected == 0) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
        } else {
            wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(expected)));
        }
    }

    @Step("Open the cart")
    public void openCart() {
        click(cartLink);
    }

    /** value: az | za | lohi | hilo */
    @Step("Sort products by '{value}'")
    public void sortBy(String value) {
        new Select(visible(sortSelect)).selectByValue(value);
    }

    @Step("Read product names")
    public List<String> getProductNames() {
        visible(itemNames);
        return driver.findElements(itemNames).stream().map(e -> e.getText().trim()).toList();
    }

    @Step("Read product prices")
    public List<Double> getProductPrices() {
        visible(itemPrices);
        return driver.findElements(itemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "").trim()))
                .toList();
    }
}
