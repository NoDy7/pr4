package tests;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.InventoryPage;
import pages.LoginPage;
import utils.AllureAttachments;
import utils.Config;
import utils.DriverFactory;
import utils.EnvironmentWriter;

public abstract class BaseTest {

    protected WebDriver driver;

    /**
     * Browser resolution order: testng.xml parameter -> -Dbrowser -> chrome.
     * (Instance field is safe with parallel="tests": every <test> gets its own class instances.)
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(@Optional("") String browser, @Optional("") String headless) {
        String b = browser.isBlank() ? System.getProperty("browser", "chrome") : browser;
        boolean h = Boolean.parseBoolean(headless.isBlank()
                ? System.getProperty("headless", "false") : headless);

        driver = DriverFactory.create(b, h);
        driver.manage().window().maximize();

        Capabilities caps = ((HasCapabilities) driver).getCapabilities();
        EnvironmentWriter.registerBrowser(caps.getBrowserName() + " " + caps.getBrowserVersion());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            try {
                if (!result.isSuccess()) {
                    AllureAttachments.screenshot(driver, "Failure screenshot");
                    AllureAttachments.pageSource(driver, "Page source on failure");
                    AllureAttachments.currentUrl(driver);
                }
            } finally {
                driver.quit();
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public void writeAllureEnvironment() {
        EnvironmentWriter.write();
    }

    /** Shared precondition: fresh session, logged in as standard_user. */
    protected InventoryPage loginAsStandardUser() {
        new LoginPage(driver).open().login(Config.STANDARD_USER, Config.PASSWORD);
        InventoryPage inventory = new InventoryPage(driver);
        inventory.waitUntilOpened();
        return inventory;
    }
}
