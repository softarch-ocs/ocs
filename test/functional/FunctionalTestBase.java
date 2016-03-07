package functional;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(JUnit4.class)
public class FunctionalTestBase {
    protected WebDriver driver;
    protected String baseUrl;
    
    @Rule
    public KnownDatabaseState state = new KnownDatabaseState();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/ocs/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
    
    protected String getInnerHtml(WebElement element) {
        return ((JavascriptExecutor)driver).executeScript(
                "return arguments[0].innerHTML", element).toString();
    }
    
    protected void loginAs(String userName, String password) {
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("j_idt18:inputEmail")).clear();
        driver.findElement(By.id("j_idt18:inputEmail")).sendKeys(userName);
        driver.findElement(By.id("j_idt18:inputPassword")).clear();
        driver.findElement(By.id("j_idt18:inputPassword")).sendKeys(password);
        driver.findElement(By.name("j_idt18:j_idt22")).click();
    }
    
    protected void loginAsSampleUser() {
        loginAs("ah@ah.com", "ah@ah.com");
    }
    
    protected void loginAsSampleAdmin() {
        loginAs("admin@ocs.com", "admin@ocs.com");
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
