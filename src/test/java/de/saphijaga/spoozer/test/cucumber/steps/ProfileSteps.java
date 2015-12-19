package de.saphijaga.spoozer.test.cucumber.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.saphijaga.spoozer.test.cucumber.BaseIntegration;
import de.saphijaga.spoozer.test.cucumber.SpringBootTest;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.name;

/**
 * Created by samuel on 19.12.15.
 */
public class ProfileSteps extends SpringBootTest {
    @When("^the profile button is clicked$")
    public void theProfileButtonIsClicked() throws Throwable {
        driver.findElement(cssSelector(".top-bar a[href=\"#/profile\"]")).click();
    }

    @And("^the change password button is clicked$")
    public void theChangePasswordButtonIsClicked() throws Throwable {
        driver.findElement(cssSelector("dd[active=\"tabs.password.active\"] a")).click();
    }

    @When("^the old password is \"([^\"]*)\"$")
    public void theOldPasswordIs(String password) throws Throwable {
        driver.findElement(name("oldpassword")).sendKeys(password);
    }

    @And("^the new password is \"([^\"]*)\"$")
    public void theNewPasswordIs(String password) throws Throwable {
        driver.findElement(name("password")).sendKeys(password);
    }

    @And("^the confirmed password is \"([^\"]*)\"$")
    public void theConfirmedPasswordIs(String password) throws Throwable {
        driver.findElement(name("password2")).sendKeys(password);
    }

    @And("^the save button is clicked$")
    public void theSaveButtonIsClicked() throws Throwable {
        WebElement changePasswordForm = driver.findElement(cssSelector("form[ng-submit=\"changePassword()\"]"));
        changePasswordForm.submit();
        new WebDriverWait(driver, BaseIntegration.TIMEOUT).until(ExpectedConditions.visibilityOf(changePasswordForm));
    }

    @Then("^the password is changed$")
    public void thePasswordIsChanged() throws Throwable {
        WebElement element = driver.findElement(cssSelector("dd[active=\"tabs.overview.active\"]"));
        assertTrue(Arrays.stream(element.getAttribute("class").split(" ")).anyMatch(c -> c.equals("active")));
    }
}