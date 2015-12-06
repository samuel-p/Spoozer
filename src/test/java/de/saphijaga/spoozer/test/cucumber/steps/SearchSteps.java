package de.saphijaga.spoozer.test.cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.saphijaga.spoozer.test.cucumber.BrowserHolder;
import de.saphijaga.spoozer.test.cucumber.SpringBootTest;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.cssSelector;

/**
 * Created by samuel on 02.12.15.
 */
public class SearchSteps extends SpringBootTest {
    @Autowired
    private BrowserHolder browser;

    @Given("^the search text is \"([^\"]*)\"$")
    public void theSearchTextIs(String text) {
        WebElement search = browser.getDriver().findElement(cssSelector(".top-bar input[ng-model=\"text\"]"));
        System.out.println(search.getLocation());
        System.out.println(search.getSize());
        search.sendKeys(text);
    }

    @When("^the search button is clicked$")
    public void theSearchButtonIsClicked() {
        browser.getDriver().findElement(cssSelector(".top-bar form[ng-submit=\"search()\"]")).submit();
    }

    @Then("^the search results are shown$")
    public void theSearchResultsAreShown() {
        // TODO
    }

    @Then("^the search result is empty$")
    public void theSearchResultIsEmpty() {
        WebElement element = browser.getDriver().findElement(cssSelector(".ng-scope p"));
        assertEquals("Empty result is invalid", "Keine Ergebnisse gefunden!", element.getText());
    }
}