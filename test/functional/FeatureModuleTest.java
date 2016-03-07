package functional;

import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class FeatureModuleTest extends FunctionalTestBase {

    @Before
    public void before() {
        driver.get(baseUrl);
        loginAs("admin@ocs.com", "admin@ocs.com");
    }

    @Test
    public void testFeaturesCreation() throws Exception {
        // Ir a la vista de características
        driver.findElement(By.linkText("Features")).click();
        assertEquals("Features", driver.findElement(By.cssSelector("h2")).getText());
        // Crear una nueva característica
        driver.findElement(By.linkText("Create Feature")).click();
        assertEquals("Create New Feature", driver.findElement(By.cssSelector("legend")).getText());
        driver.findElement(By.id("j_idt22:nameInput")).clear();
        driver.findElement(By.id("j_idt22:nameInput")).sendKeys(".NET");
        driver.findElement(By.id("j_idt22:descriptionInput")).clear();
        driver.findElement(By.id("j_idt22:descriptionInput")).sendKeys("Web Service Framework.");
        driver.findElement(By.name("j_idt22:j_idt28")).click();
        // Verificación
        assertEquals(".NET", driver.findElement(By.linkText(".NET")).getText());
        assertEquals("Web Service Framework.", driver.findElement(By.xpath("//tr[11]/td[2]")).getText());
    }

    @Test
    public void testFeaturesEdit() throws Exception {
        // Ir a la vista de características
        driver.findElement(By.linkText("Features")).click();
        assertEquals("Features", driver.findElement(By.cssSelector("h2")).getText());
        // Editar una característica
        driver.findElement(By.linkText("Manejo de personas")).click();
        assertEquals("Manejo de personas", driver.findElement(By.cssSelector("legend")).getText());
        driver.findElement(By.id("j_idt22:nameInput")).clear();
        driver.findElement(By.id("j_idt22:nameInput")).sendKeys("Relaciones Interpersonales");
        driver.findElement(By.id("j_idt22:descriptionInput")).clear();
        driver.findElement(By.id("j_idt22:descriptionInput")).sendKeys("Experiencia y habilidad en las relaciones interpersonales.");
        driver.findElement(By.name("j_idt22:j_idt28")).click();
        // Verificación
        assertEquals("Relaciones Interpersonales", driver.findElement(By.linkText("Relaciones Interpersonales")).getText());
        assertEquals("Experiencia y habilidad en las relaciones interpersonales.", driver.findElement(By.xpath("//tr[8]/td[2]")).getText());
    }

    @Test
    public void testFeaturesJob() throws Exception {
        // Ir a la vista de Jobs
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Jobs')])[2]")).click();
        assertEquals("Current Jobs", driver.findElement(By.cssSelector("h2")).getText());
        // Ir a la vista de características de un trabajo
        driver.findElement(By.xpath("(//a[contains(text(),'Show Features')])[5]")).click();
        assertEquals("Job Features", driver.findElement(By.cssSelector("h2")).getText());
        // Eliminar algunas características
        driver.findElement(By.xpath("(//a[contains(text(),'delete Feature')])[2]")).click();
        assertThat("Manejo de recursos", is(not(driver.findElement(By.xpath("//tr[2]/td")).getText())));
        // Agregar nuevas características
        driver.findElement(By.linkText("Add Feature")).click();
        assertEquals("Features", driver.findElement(By.cssSelector("h2")).getText());
        driver.findElement(By.xpath("(//a[contains(text(),'add Feature')])[7]")).click();
        driver.findElement(By.linkText("Accept")).click();
        // Verificación
        assertEquals("Job Features", driver.findElement(By.cssSelector("h2")).getText());
        assertEquals("Manejo de personas", driver.findElement(By.xpath("//tr[3]/td")).getText());
    }

    @Test
    public void testFeaturesUser() throws Exception {
        // Ir a la vista de Users
        driver.findElement(By.linkText("Users")).click();
        assertEquals("User list", driver.findElement(By.cssSelector("h1")).getText());
        // Ir a la vista de características del usuario
        driver.findElement(By.xpath("(//a[contains(text(),'Show Features')])[7]")).click();
        assertEquals("User Features", driver.findElement(By.cssSelector("h2")).getText());
        // Eliminar algunas características
        driver.findElement(By.xpath("(//a[contains(text(),'delete Feature')])[4]")).click();
        assertThat("Trabajo en equipo", is(not(driver.findElement(By.xpath("//tr[4]/td")).getText())));
        driver.findElement(By.linkText("delete Feature")).click();
        assertThat("Excel", is(not(driver.findElement(By.cssSelector("td")).getText())));
        // Agregar nuevas características
        driver.findElement(By.linkText("Add Feature")).click();
        assertEquals("Features", driver.findElement(By.cssSelector("h2")).getText());
        driver.findElement(By.xpath("(//a[contains(text(),'add Feature')])[3]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'add Feature')])[2]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'add Feature')])[2]")).click();
        driver.findElement(By.linkText("Accept")).click();
        // Verificación
        assertEquals("User Features", driver.findElement(By.cssSelector("h2")).getText());
        assertEquals("C++", driver.findElement(By.xpath("//tr[4]/td")).getText());
        assertEquals("Java", driver.findElement(By.xpath("//tr[5]/td")).getText());
        assertEquals("Selenium.", driver.findElement(By.xpath("//tr[6]/td")).getText());
    }

}
