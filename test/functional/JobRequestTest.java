package functional;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class JobRequestTest extends FunctionalTestBase {

    @Before
    public void before() {
        driver.get(baseUrl + "index.xhtml");
    }

    @Test
    public void test_currentlyWorkingTest() throws Exception {
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("j_idt18:inputPassword")).clear();
        driver.findElement(By.id("j_idt18:inputPassword")).sendKeys("cl@gmail.com");
        driver.findElement(By.id("j_idt18:inputEmail")).clear();
        driver.findElement(By.id("j_idt18:inputEmail")).sendKeys("cl@gmail.com");
        driver.findElement(By.name("j_idt18:j_idt22")).click();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(By.linkText("Postulate")).click();
        assertEquals("Postulate to job", driver.findElement(By.cssSelector("h1")).getText());
        new Select(driver.findElement(By.id("j_idt22:selectJob"))).selectByVisibleText("Administrador de empresas");
        driver.findElement(By.name("j_idt39:j_idt41")).click();
        assertEquals("Sorry, you are currently working in this job.", driver.findElement(By.cssSelector("div.alert.alert-danger > ul > li")).getText());
    }

    @Test
    public void test_userCantApplyDontFulfillsJobFeatures() throws Exception {
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("j_idt18:inputPassword")).clear();
        driver.findElement(By.id("j_idt18:inputPassword")).sendKeys("pau@p.c");
        driver.findElement(By.id("j_idt18:inputEmail")).clear();
        driver.findElement(By.id("j_idt18:inputEmail")).sendKeys("pau@p.c");
        driver.findElement(By.name("j_idt18:j_idt22")).click();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(By.linkText("Postulate")).click();
        assertEquals("Postulate to job", driver.findElement(By.cssSelector("h1")).getText());
        new Select(driver.findElement(By.id("j_idt22:selectJob"))).selectByVisibleText("Publicista");
        // ERROR: Caught exception [ERROR: Unsupported command [getSelectedLabel | id=j_idt22:selectJob | ]]
        driver.findElement(By.name("j_idt39:j_idt41")).click();
        assertEquals("Sorry, you don't fulfill the necessary requirements to postulate to this job", driver.findElement(By.cssSelector("div.alert.alert-danger > ul > li")).getText());
    }

    @Test
    public void test_userPostulateJob() throws Exception {
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("j_idt18:inputPassword")).clear();
        driver.findElement(By.id("j_idt18:inputPassword")).sendKeys("ah@ah.com");
        driver.findElement(By.id("j_idt18:inputEmail")).clear();
        driver.findElement(By.id("j_idt18:inputEmail")).sendKeys("ah@ah.com");
        driver.findElement(By.name("j_idt18:j_idt22")).click();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(By.linkText("Postulate")).click();
        new Select(driver.findElement(By.id("j_idt22:selectJob"))).selectByVisibleText("Ingenierio de testing");
        new Select(driver.findElement(By.id("j_idt22:selectJob"))).selectByVisibleText("Ingenierio");
        // ERROR: Caught exception [ERROR: Unsupported command [getSelectedLabel | id=j_idt22:selectJob | ]]
        driver.findElement(By.name("j_idt39:j_idt41")).click();
        assertEquals("Organizational Coverage System", driver.findElement(By.cssSelector("h1")).getText());
    }

    @Test
    public void test_acceptJobRequest() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAsSampleAdmin();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(By.linkText("Requests")).click();
        driver.findElement(By.linkText("Paula Q")).click();
        // ERROR: Caught exception [ERROR: Unsupported command [getSelectedLabel | id=j_idt25:inputStatus | ]]
        assertEquals("Review a job request", driver.findElement(By.cssSelector("h1")).getText());
        // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
        driver.findElement(By.name("j_idt25:j_idt28")).click();
        assertEquals("ACCEPTED", driver.findElement(By.xpath("//div[2]/div/table/tbody/tr[2]/td[3]")).getText());
    }

    @Test
    public void test_rejectJobRequest() throws Exception {
        driver.get(baseUrl + "index.xhtml");
        loginAsSampleAdmin();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(By.linkText("Requests")).click();
        driver.findElement(By.linkText("Hernan Wess")).click();
        new Select(driver.findElement(By.id("j_idt25:inputStatus"))).selectByVisibleText("Reject");
        assertEquals("Review a job request", driver.findElement(By.cssSelector("h1")).getText());
        // ERROR: Caught exception [ERROR: Unsupported command [getSelectedLabel | id=j_idt25:inputStatus | ]]
        driver.findElement(By.name("j_idt25:j_idt28")).click();
        assertEquals("Hernan Wess", driver.findElement(By.xpath("//tr[6]/td")).getText());
        assertEquals("Administrador de empresas", driver.findElement(By.xpath("//tr[6]/td[2]")).getText());
        assertEquals("REJECTED", driver.findElement(By.xpath("//tr[6]/td[3]")).getText());
    }

}
