package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;

/**
 * Fails ON PURPOSE (wrong expected title) so that the Allure report contains a failed card
 * with a screenshot, page source and URL. Not part of the normal suites;
 * run with: mvn clean test -Pfailure-demo
 */
@Epic("Swag Labs")
@Feature("Report demo")
public class FailureDemoTest extends BaseTest {

    @Test(description = "Intentional failure to demonstrate Allure attachments")
    @Severity(SeverityLevel.TRIVIAL)
    public void intentionallyFailingTest() {
        InventoryPage inventory = loginAsStandardUser();
        Assert.assertEquals(inventory.getTitle(), "Not the real title",
                "Intentional failure: title does not match (demo for Allure attachments)");
    }
}
