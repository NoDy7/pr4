package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

/** Creates a driver for the requested browser. Drivers are resolved by Selenium Manager. */
public final class DriverFactory {
    private DriverFactory() {}

    public static WebDriver create(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions fo = new FirefoxOptions();
                if (headless) {
                    fo.addArguments("-headless", "--width=1920", "--height=1080");
                }
                return new FirefoxDriver(fo);
            }
            case "edge" -> {
                EdgeOptions eo = new EdgeOptions();
                eo.addArguments("--remote-allow-origins=*");
                eo.setExperimentalOption("prefs", passwordManagerOff());
                if (headless) {
                    eo.addArguments("--headless=new", "--window-size=1920,1080");
                }
                return new EdgeDriver(eo);
            }
            case "chrome" -> {
                return chrome(headless);
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: '" + browser + "'. Use chrome | firefox | edge");
        }
    }

    private static WebDriver chrome(boolean headless) {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        co.setExperimentalOption("prefs", passwordManagerOff());
        if (headless) {
            co.addArguments("--headless=new", "--window-size=1920,1080");
        }
        return new ChromeDriver(co);
    }

    /** Disables password-manager / data-breach popups that can cover the page. */
    private static Map<String, Object> passwordManagerOff() {
        return Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false);
    }
}
