package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;

@Epic("Swag Labs")
@Feature("Authentication")
@Owner("Фамилия Имя")
public class LoginTest extends BaseTest {

    @Test(description = "Positive: standard_user lands on /inventory.html with 'Products' title")
    @Story("Successful login")
    @Severity(SeverityLevel.BLOCKER)
    public void positiveLogin() {
        new LoginPage(driver).open().login(Config.STANDARD_USER, Config.PASSWORD);
        InventoryPage inventory = new InventoryPage(driver);
        inventory.waitUntilOpened();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "Expected URL to contain /inventory.html but was: " + driver.getCurrentUrl());
        Assert.assertEquals(inventory.getTitle(), "Products", "Unexpected page title");
    }

    @Test(description = "Negative: locked_out_user sees an error")
    @Story("Rejected login")
    @Severity(SeverityLevel.CRITICAL)
    public void negativeLockedOutUser() {
        LoginPage page = new LoginPage(driver).open();
        page.login(Config.LOCKED_OUT_USER, Config.PASSWORD);

        String error = page.getErrorMessage();
        Assert.assertTrue(error.contains("Epic sadface") && error.contains("locked out"),
                "Expected 'locked out' error but got: " + error);
    }

    @Test(description = "Negative: wrong password sees an error")
    @Story("Rejected login")
    @Severity(SeverityLevel.NORMAL)
    public void negativeWrongPassword() {
        LoginPage page = new LoginPage(driver).open();
        page.login(Config.STANDARD_USER, "wrong_password");

        String error = page.getErrorMessage();
        Assert.assertTrue(error.contains("do not match"),
                "Expected 'do not match' error but got: " + error);
    }
}
