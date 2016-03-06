package functional;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class UserModuleTest extends FunctionalTestBase {
  @Test
  public void testLoginAsAdmin() throws Exception {
    driver.get(baseUrl + "index.xhtml");
    loginAs("admin@ocs.com", "admin@ocs.com");
    assertEquals("Signed in as Admin 1", driver.findElement(By.id("loginText")).getText());
  }
  
  @Test
  public void testLoginAsUser() throws Exception {
    driver.get(baseUrl + "index.xhtml");
    loginAs("ah@ah.com", "ah@ah.com");
    assertEquals("Signed in as Andres Hernandez", driver.findElement(By.id("loginText")).getText());
  }
}
