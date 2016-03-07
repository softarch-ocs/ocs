/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.google.common.base.Predicate;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 *
 * @author Felipe
 */
public class StatisticsModuleTest extends FunctionalTestBase {
 
  @Before
  public void before(){
    driver.get(baseUrl + "index.xhtml");
    loginAs("admin@ocs.com", "admin@ocs.com");
    driver.findElement(By.linkText("Statistics")).click(); 
  }
  
  @Test
  public void testStatisticsArea() throws Exception {
    assertEquals("Finanzas", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(1) td:nth-of-type(1)"))));
    assertEquals("1", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(1) td:nth-of-type(2)"))));
    assertEquals("Recursos humanos", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(2) td:nth-of-type(1)"))));
    assertEquals("1", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(2) td:nth-of-type(2)"))));
    assertEquals("Administracion", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(3) td:nth-of-type(1)"))));
    assertEquals("1", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(3) td:nth-of-type(2)"))));
    assertEquals("Testing", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(4) td:nth-of-type(1)"))));
    assertEquals("3", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(4) td:nth-of-type(2)"))));
    assertEquals("Ingenieria", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(5) td:nth-of-type(1)"))));
    assertEquals("2", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(5) td:nth-of-type(2)"))));
    assertEquals("Mercadeo", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(6) td:nth-of-type(1)"))));
    assertEquals("1", getInnerHtml(driver.findElement(By.cssSelector("#areaDiv table tbody tr:nth-of-type(6) td:nth-of-type(2)"))));
  }
  
  @Test
  public void testStatisticsGender() throws Exception {
    assertEquals("FEMALE", getInnerHtml(driver.findElement(By.cssSelector("#genderDiv table tbody tr:nth-of-type(1) td:nth-of-type(1)"))));
    assertEquals("4", getInnerHtml(driver.findElement(By.cssSelector("#genderDiv table tbody tr:nth-of-type(1) td:nth-of-type(2)"))));
    assertEquals("MALE", getInnerHtml(driver.findElement(By.cssSelector("#genderDiv table tbody tr:nth-of-type(2) td:nth-of-type(1)"))));
    assertEquals("5", getInnerHtml(driver.findElement(By.cssSelector("#genderDiv table tbody tr:nth-of-type(2) td:nth-of-type(2)"))));
  }
  
  @Test
  public void testStatisticsAge() throws Exception {
    assertEquals("51 - 60", getInnerHtml(driver.findElement(By.cssSelector("#ageDiv table tbody tr:nth-of-type(1) td:nth-of-type(1)"))));
    assertEquals("2", getInnerHtml(driver.findElement(By.cssSelector("#ageDiv table tbody tr:nth-of-type(1) td:nth-of-type(2)"))));
    assertEquals("21 - 30", getInnerHtml(driver.findElement(By.cssSelector("#ageDiv table tbody tr:nth-of-type(2) td:nth-of-type(1)"))));
    assertEquals("2", getInnerHtml(driver.findElement(By.cssSelector("#ageDiv table tbody tr:nth-of-type(1) td:nth-of-type(2)"))));
    assertEquals("31 - 40", getInnerHtml(driver.findElement(By.cssSelector("#ageDiv table tbody tr:nth-of-type(3) td:nth-of-type(1)"))));
    assertEquals("5", getInnerHtml(driver.findElement(By.cssSelector("#ageDiv table tbody tr:nth-of-type(3) td:nth-of-type(2)"))));
  }
}   
