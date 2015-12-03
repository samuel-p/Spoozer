package de.saphijaga.spoozer.cucumber.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import de.saphijaga.spoozer.cucumber.BrowserHolder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import static de.saphijaga.spoozer.cucumber.BaseIntegration.TIMEOUT;
import static de.saphijaga.spoozer.cucumber.BaseIntegration.chromeDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by samuel on 02.12.15.
 */
public class BaseSteps {
    @Autowired
    private BrowserHolder browser;

    @Autowired
    private LoginSteps login;

    @Before
    public void setUp() {
        browser.setDriver(chromeDriver());
        browser.getDriver().get("http://localhost:8080/login");
    }

    @Given("^the user with username \"([^\"]*)\" and password \"([^\"]*)\" is logged in$")
    public void theUserIsLoggedIn(String username, String password) throws Exception {
        login.theUsernameIsAndThePasswordIs(username, password);
        login.theLoginButtonIsClicked();
    }

    @Given("^a view is shown$")
    public void aViewIsShown() {
        ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), 30);
        wait.until(pageLoadCondition);
    }

    @Then("^the ([a-z]*) is shown$")
    public void theViewIsShown(String view) throws Exception {
        assertEquals("Application title invalid", "Spoozer", browser.getDriver().getTitle());
        new WebDriverWait(browser.getDriver(), TIMEOUT).until(ExpectedConditions.urlContains(view));
        assertTrue("View invalid", browser.getDriver().getCurrentUrl().contains(view));
    }

    @Then("^the ([a-z]*) is not shown$")
    public void theViewIsNotShown(String view) throws Exception {
        assertFalse("View invalid", browser.getDriver().getCurrentUrl().contains(view));
    }

    @Then("^the ([a-z]*) page is shown$")
    public void thePageIsShown(String page) throws Exception {
        assertTrue("View invalid", browser.getDriver().getCurrentUrl().endsWith(page + "?error"));
    }

    @After
    public void tearDown() {
        browser.getDriver().quit();
    }

}