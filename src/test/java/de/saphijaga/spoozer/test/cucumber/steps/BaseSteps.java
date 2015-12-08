package de.saphijaga.spoozer.test.cucumber.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import de.saphijaga.spoozer.test.cucumber.BrowserHolder;
import de.saphijaga.spoozer.test.cucumber.SpringBootTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import static de.saphijaga.spoozer.test.cucumber.BaseIntegration.TIMEOUT;
import static de.saphijaga.spoozer.test.cucumber.BaseIntegration.chromeDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by samuel on 02.12.15.
 */
public class BaseSteps extends SpringBootTest {
    @Autowired
    private BrowserHolder browser;

    @Autowired
    private LoginSteps login;

    @Before
    public void set_up() {
        browser.setDriver(chromeDriver());
        browser.getDriver().get("http://localhost:8080/login");
    }

    @Given("^the user with username \"([^\"]*)\" and password \"([^\"]*)\" is logged in$")
    public void the_user_is_logged_in(String username, String password) throws Exception {
        login.the_username_is(username);
        login.the_password_is(password);
        login.the_login_button_is_clicked();
    }

    @Given("^a view is shown$")
    public void a_view_is_shown() {
        ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), 30);
        wait.until(pageLoadCondition);
    }

    @Then("^the ([a-z]*) is shown$")
    public void the_view_is_shown(String view) throws Exception {
        assertEquals("Application title invalid", "Spoozer", browser.getDriver().getTitle());
        new WebDriverWait(browser.getDriver(), TIMEOUT).until(ExpectedConditions.urlContains(view));
        assertTrue("View invalid", browser.getDriver().getCurrentUrl().contains(view));
    }

    @Then("^the ([a-z]*) is not shown$")
    public void the_view_is_not_shown(String view) throws Exception {
        assertFalse("View invalid", browser.getDriver().getCurrentUrl().contains(view));
    }

    @Then("^the ([a-z]*) page is shown$")
    public void the_page_is_shown(String page) throws Exception {
        assertTrue("View invalid", browser.getDriver().getCurrentUrl().contains(page));
    }

    @After
    public void tear_down() {
        browser.getDriver().quit();
    }

}