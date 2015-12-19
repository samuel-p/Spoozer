package de.saphijaga.spoozer.test.cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.saphijaga.spoozer.test.cucumber.BaseIntegration;
import de.saphijaga.spoozer.test.cucumber.SpringBootTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.*;

/**
 * Created by samuel on 08.12.15.
 */
public class SignupSteps extends SpringBootTest {
    @Given("^the register link is clicked$")
    public void the_register_link_is_clicked() throws Throwable {
        driver.findElement(cssSelector("[href=\"/register\"]")).click();
        new WebDriverWait(driver, BaseIntegration.TIMEOUT).until(ExpectedConditions.urlContains("/register"));
    }

    @And("^the email is \"([^\"]*)\"$")
    public void the_email_is(String email) throws Throwable {
        driver.findElement(name("email")).sendKeys(email);
    }

    @And("^the password confirm is \"([^\"]*)\"$")
    public void the_password_confirm_is(String password) throws Throwable {
        driver.findElement(name("password2")).sendKeys(password);
    }

    @When("^the register button is clicked$")
    public void the_register_button_is_clicked() throws Throwable {
        WebElement loginForm = driver.findElement(className("form-login"));
        loginForm.submit();
        new WebDriverWait(driver, BaseIntegration.TIMEOUT).until(ExpectedConditions.stalenessOf(loginForm));
    }

    @Then("^the error message is \"([^\"]*)\"")
    public void the_error_message_is_shown(String message) throws Exception {
        WebElement errorBox = driver.findElement(cssSelector("small.error"));
        assertEquals("Message is invalid", errorBox.getText(), message);
    }
}