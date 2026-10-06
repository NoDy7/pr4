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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Epic("Swag Labs")
@Feature("Product sorting")
@Owner("Фамилия Имя")
public class SortingTest extends BaseTest {

    @Test(description = "Sort by name A to Z")
    @Story("Sort by name")
    @Severity(SeverityLevel.NORMAL)
    public void sortNameAscending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("az");

        List<String> actual = inventory.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Products are not sorted A to Z");
    }

    @Test(description = "Sort by name Z to A")
    @Story("Sort by name")
    @Severity(SeverityLevel.NORMAL)
    public void sortNameDescending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("za");

        List<String> actual = inventory.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Products are not sorted Z to A");
    }

    @Test(description = "Sort by price low to high")
    @Story("Sort by price")
    @Severity(SeverityLevel.NORMAL)
    public void sortPriceAscending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("lohi");

        List<Double> actual = inventory.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Prices are not sorted low to high");
    }

    @Test(description = "Sort by price high to low")
    @Story("Sort by price")
    @Severity(SeverityLevel.NORMAL)
    public void sortPriceDescending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("hilo");

        List<Double> actual = inventory.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Prices are not sorted high to low");
    }
}
