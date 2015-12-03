package de.saphijaga.spoozer.cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;
import de.saphijaga.spoozer.cucumber.BrowserHolder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import static de.saphijaga.spoozer.cucumber.BaseIntegration.TIMEOUT;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.*;

public class LoginSteps {
    @Autowired
    private BrowserHolder browser;

    @Given("^the username is \"([^\"]*)\" and the password is \"([^\"]*)\"$")
    public void theUsernameIsAndThePasswordIs(String username, String password) throws Exception {
        browser.getDriver().findElement(name("username")).sendKeys(username);
        browser.getDriver().findElement(name("password")).sendKeys(password);
    }

    @When("^the login button is clicked$")
    public void theLoginButtonIsClicked() throws Exception {
        WebElement loginForm = browser.getDriver().findElement(className("form-login"));
        loginForm.submit();
        new WebDriverWait(browser.getDriver(), TIMEOUT).until(ExpectedConditions.stalenessOf(loginForm));
    }

    @Then("^the error message is \"([^\"]*)\"")
    public void theErrorMessageIsShown(String message) throws Exception {
        WebElement alertBox = browser.getDriver().findElement(cssSelector(".alert-box.alert"));
        assertEquals("Message is invalid", alertBox.getText(), message);
    }
}