package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Config;

public class LoginPage extends BasePage {

    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open the login page")
    public LoginPage open() {
        driver.get(Config.BASE_URL + "/");
        visible(loginButton);
        return this;
    }

    @Step("Log in as {user}")
    public void login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
    }

    @Step("Read the login error message")
    public String getErrorMessage() {
        return text(error);
    }
}
