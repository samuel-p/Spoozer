package de.saphijaga.spoozer.test.cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.saphijaga.spoozer.test.cucumber.BaseIntegration;
import de.saphijaga.spoozer.test.cucumber.BrowserHolder;
import de.saphijaga.spoozer.test.cucumber.SpringBootTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.*;

public class LoginSteps extends SpringBootTest {
    @Autowired
    private BrowserHolder browser;

    @Given("^the username is \"([^\"]*)\"$")
    public void the_username_is(String username) throws Exception {
        browser.getDriver().findElement(name("username")).sendKeys(username);
    }

    @Given("^the password is \"([^\"]*)\"$")
    public void the_password_is(String password) throws Exception {
        browser.getDriver().findElement(name("password")).sendKeys(password);
    }

    @When("^the login button is clicked$")
    public void the_login_button_is_clicked() throws Exception {
        WebElement loginForm = browser.getDriver().findElement(className("form-login"));
        loginForm.submit();
        new WebDriverWait(browser.getDriver(), BaseIntegration.TIMEOUT).until(ExpectedConditions.stalenessOf(loginForm));
    }

    @Then("^the alert message is \"([^\"]*)\"")
    public void the_alert_message_is_shown(String message) throws Exception {
        WebElement alertBox = browser.getDriver().findElement(cssSelector(".alert-box.alert"));
        assertEquals("Message is invalid", alertBox.getText(), message);
    }
}