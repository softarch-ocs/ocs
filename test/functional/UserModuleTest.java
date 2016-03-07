package functional;

import static org.hamcrest.Matchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class UserModuleTest extends FunctionalTestBase {

    @Test
    public void testExistingAdmin_CanLogin() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAs("admin@ocs.com", "admin@ocs.com");
        assertEquals("Signed in as Admin 1", driver.findElement(By.id("loginText")).getText());
    }

    @Test
    public void testExistingUser_CanLogin() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAs("ah@ah.com", "ah@ah.com");
        assertEquals("Signed in as Andres Hernandez", driver.findElement(By.id("loginText")).getText());
    }

    @Test
    public void testGuest_CanRegister() throws Exception {
        driver.get(baseUrl + "index.xhtml");

        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("j_idt18:inputFirstName")).clear();
        driver.findElement(By.id("j_idt18:inputFirstName")).sendKeys("First");
        driver.findElement(By.id("j_idt18:inputLastName")).clear();
        driver.findElement(By.id("j_idt18:inputLastName")).sendKeys("Last");
        driver.findElement(By.id("j_idt18:inputBirthday")).clear();
        driver.findElement(By.id("j_idt18:inputBirthday")).sendKeys("1994-01-01");
        driver.findElement(By.id("j_idt18:inputPersonalId")).clear();
        driver.findElement(By.id("j_idt18:inputPersonalId")).sendKeys("123456789");
        driver.findElement(By.id("j_idt18:inputEmail")).clear();
        driver.findElement(By.id("j_idt18:inputEmail")).sendKeys("a@a.com");
        driver.findElement(By.id("j_idt18:inputPassword")).clear();
        driver.findElement(By.id("j_idt18:inputPassword")).sendKeys("password");
        driver.findElement(By.id("j_idt18:inputAddress")).clear();
        driver.findElement(By.id("j_idt18:inputAddress")).sendKeys("My address");
        driver.findElement(By.id("j_idt18:inputNumber")).clear();
        driver.findElement(By.id("j_idt18:inputNumber")).sendKeys("9876543210");
        driver.findElement(By.name("j_idt18:j_idt29")).click();

        assertEquals("Signed in as First Last", driver.findElement(By.id("loginText")).getText());

        // Test login with the previous password
        driver.findElement(By.id("j_idt27:logoutButton")).click();
        loginAs("a@a.com", "password");
        
        assertEquals("Signed in as First Last", driver.findElement(By.id("loginText")).getText());
    }
    
    @Test
    public void testNonExistingUser_CannotLogin() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAs("a@a.com", "password");
        
        assertThat(
                driver.findElement(By.cssSelector("div.alert.alert-danger")).getText(),
                containsString("Invalid credentials"));
    }
    
    @Test
    public void testLoggedInUser_CanLogout() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAs("ah@ah.com", "ah@ah.com");
        
        driver.findElement(By.id("j_idt27:logoutButton")).click();
        assertTrue(isElementPresent(By.linkText("Login")));
    }
}
