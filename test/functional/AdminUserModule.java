package functional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class AdminUserModule extends FunctionalTestBase {

    @Test
    public void testExistingAdmin_CanListAllUsers() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAsSampleAdmin();

        driver.findElement(By.linkText("Users")).click();

        String[][] expected = {
            {"1", "Admin", "admin@ocs.com", "123465", "FEMALE", "1993-05-05 00:00:00.0"},
            {"Diaz", "Carlos", "carlos@ocs.com", "1564877", "MALE", "1985-01-02 00:00:00.0"},
            {"Hernandez", "Andres", "ah@ah.com", "123465", "MALE", "1994-01-31 00:00:00.0"},
            {"Mendoza", "Claudia", "cl@gmail.com", "123465", "FEMALE", "1961-10-20 00:00:00.0"},
            {"Q", "Paula", "pau@p.c", "154687", "FEMALE", "1976-04-17 00:00:00.0"},
            {"Velez", "Juan", "juan@velez.c", "154687", "MALE", "1995-06-30 00:00:00.0"},
            {"Wess", "Hernan", "hernan@o.com", "123465", "MALE", "1976-04-14 00:00:00.0"},
            {"Xi", "Ximena", "xim@xi.xi", "154687", "FEMALE", "1985-01-03 00:00:00.0"},
            {"Za", "Mauricio", "mau@za.c", "465487987", "MALE", "1976-04-15 00:00:00.0"},};

        for (int i = 1; i <= expected.length; ++i) {
            for (int j = 1; j <= expected[i - 1].length; ++j) {
                assertEquals(expected[i - 1][j - 1], driver.findElement(
                        By.cssSelector("#userListTable tbody tr:nth-of-type("
                                + i + ") td:nth-of-type(" + j + ")")).getText());
            }
        }
    }

    @Test
    public void testExistingAdmin_CanViewJobHistoryForAUser() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAsSampleAdmin();

        driver.findElement(By.linkText("Users")).click();

        // Show Job History for Carlos Diaz
        driver.findElement(By.xpath("(//a[contains(text(),'View job history')])[2]"))
                .click();

        assertEquals("Job entries for Diaz Carlos",
                driver.findElement(By.xpath("//h1")).getText());

        String[][] expected = {
            {"Publicista", "2016-02-29 00:00:00.0", ""},};

        for (int i = 1; i <= expected.length; ++i) {
            for (int j = 1; j <= expected[i - 1].length; ++j) {
                assertEquals(expected[i - 1][j - 1], driver.findElement(
                        By.cssSelector("#jobHistoryTable tbody tr:nth-of-type("
                                + i + ") td:nth-of-type(" + j + ")")).getText());
            }
        }
    }

    @Test
    public void testExistingAdmin_CanEditAJobEntry() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAsSampleAdmin();

        driver.findElement(By.linkText("Users")).click();

        // Show Job History for Carlos Diaz
        driver.findElement(By.xpath("(//a[contains(text(),'View job history')])[2]"))
                .click();

        // Edit
        driver.findElement(By.linkText("Edit")).click();
        new Select(driver.findElement(By.id("j_idt28:inputJob"))).selectByVisibleText("Arquitecto");
        driver.findElement(By.id("j_idt28:inputStartDate")).clear();
        driver.findElement(By.id("j_idt28:inputStartDate")).sendKeys("2015-02-20");
        driver.findElement(By.id("j_idt28:inputEndDate")).clear();
        driver.findElement(By.id("j_idt28:inputEndDate")).sendKeys("2016-02-29");
        driver.findElement(By.name("j_idt28:j_idt34")).click();

        String[][] expected = {
            {"Arquitecto", "2015-02-20 00:00:00.0", "2016-02-29 00:00:00.0"},};

        for (int i = 1; i <= expected.length; ++i) {
            for (int j = 1; j <= expected[i - 1].length; ++j) {
                assertEquals(expected[i - 1][j - 1], driver.findElement(
                        By.cssSelector("#jobHistoryTable tbody tr:nth-of-type("
                                + i + ") td:nth-of-type(" + j + ")")).getText());
            }
        }
    }
    
    @Test
    public void testExistingAdmin_CanAddAJobEntry() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAsSampleAdmin();

        driver.findElement(By.linkText("Users")).click();

        // Show Job History for Carlos Diaz
        driver.findElement(By.xpath("(//a[contains(text(),'View job history')])[2]"))
                .click();

        // Edit
        driver.findElement(By.linkText("Add to another job")).click();
        new Select(driver.findElement(By.id("j_idt28:inputJob"))).selectByVisibleText("Arquitecto");
        driver.findElement(By.id("j_idt28:inputStartDate")).clear();
        driver.findElement(By.id("j_idt28:inputStartDate")).sendKeys("2015-02-20");
        driver.findElement(By.id("j_idt28:inputEndDate")).clear();
        driver.findElement(By.id("j_idt28:inputEndDate")).sendKeys("2016-02-29");
        driver.findElement(By.name("j_idt28:j_idt34")).click();

        String[][] expected = {
            {"Arquitecto", "2015-02-20 00:00:00.0", "2016-02-29 00:00:00.0"},
            {"Publicista", "2016-02-29 00:00:00.0", ""},
        };

        for (int i = 1; i <= expected.length; ++i) {
            for (int j = 1; j <= expected[i - 1].length; ++j) {
                assertEquals(expected[i - 1][j - 1], driver.findElement(
                        By.cssSelector("#jobHistoryTable tbody tr:nth-of-type("
                                + i + ") td:nth-of-type(" + j + ")")).getText());
            }
        }
    }

    @Test
    public void testExistingAdmin_CanDeleteAJobEntry() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAsSampleAdmin();

        driver.findElement(By.linkText("Users")).click();

        // Show Job History for Carlos Diaz
        driver.findElement(By.xpath("(//a[contains(text(),'View job history')])[2]"))
                .click();

        // Delete the first entry
        driver.findElement(By.name("j_idt22:0:j_idt24:j_idt26")).click();

        assertThat(getInnerHtml(driver.findElement(
                By.cssSelector("#jobHistoryTable tbody"))), not(containsString("Publicista")));
    }
}
