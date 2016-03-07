package functional;

import org.junit.Before;
import org.openqa.selenium.By;
import com.google.common.base.Predicate;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandleJobsTest extends FunctionalTestBase{
  @Before
  public void before(){
    driver.get(baseUrl + "index.xhtml");
    loginAs("admin@ocs.com", "admin@ocs.com");
  }
  
  @Test
  public void testCurrentJobs() throws Exception {
    driver.findElement(By.linkText("Jobs")).click();
    driver.findElement(By.xpath("(//a[contains(text(),'Jobs')])[2]")).click();
    try {
      assertTrue(isElementPresent(By.linkText("Administrador de empresas")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.linkText("Arquitecto")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.linkText("Ingenierio")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.linkText("Ingenierio de testing")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.linkText("Ingeniero industrial")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.linkText("Publicista")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    assertEquals("112111", driver.findElement(By.xpath("//td[2]")).getText());
    assertEquals("15000000", driver.findElement(By.xpath("//tr[2]/td[2]")).getText());
    assertEquals("Desarrolla el software.", driver.findElement(By.xpath("//tr[3]/td[3]")).getText());
    assertEquals("optimizar jajaja", driver.findElement(By.xpath("//tr[5]/td[3]")).getText());
    assertEquals("Realiza pruebas de la aplicacion.", driver.findElement(By.xpath("//tr[4]/td[3]")).getText());
    assertEquals("Crea la publicidad", driver.findElement(By.xpath("//tr[6]/td[3]")).getText());
    assertEquals("Finanzas", driver.findElement(By.xpath("//td[4]")).getText());
    assertEquals("Administracion", driver.findElement(By.xpath("//tr[2]/td[4]")).getText());
    assertEquals("Administracion", driver.findElement(By.xpath("//tr[2]/td[4]")).getText());
    assertEquals("Ingenieria", driver.findElement(By.xpath("//tr[3]/td[4]")).getText());
    assertEquals("Testing", driver.findElement(By.xpath("//tr[4]/td[4]")).getText());
    assertEquals("Recursos humanos", driver.findElement(By.xpath("//tr[5]/td[4]")).getText());
    assertEquals("Mercadeo", driver.findElement(By.xpath("//tr[6]/td[4]")).getText());
  }
  
}
