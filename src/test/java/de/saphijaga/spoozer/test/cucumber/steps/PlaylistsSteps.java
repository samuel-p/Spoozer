package de.saphijaga.spoozer.test.cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.saphijaga.spoozer.test.cucumber.SpringBootTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static de.saphijaga.spoozer.test.cucumber.BaseIntegration.TIMEOUT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.openqa.selenium.By.*;

/**
 * Created by samuel on 20.12.15.
 */
public class PlaylistsSteps extends SpringBootTest {
    private int playlistCounter;

    @When("^the playlists button is clicked$")
    public void thePlaylistsButtonIsClicked() throws Throwable {
        driver.findElement(cssSelector(".sidebar a[href=\"#/playlists\"]")).click();
    }

    @When("^the add playlist button is clicked$")
    public void theAddPlaylistButtonIsClicked() throws Throwable {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(className("loading-view")));
        driver.findElement(cssSelector("button[ng-click=\"showAddPlaylist()\"]")).click();
    }


    @And("^the playlist name is \"([^\"]*)\"$")
    public void thePlaylistNameIs(String name) throws Throwable {
        driver.findElement(cssSelector("input[ng-model=\"name\"]")).sendKeys(name);
    }

    @And("^the enter key is pressed$")
    public void theEnterKeyIsPressed() throws Throwable {
        driver.findElement(cssSelector("input[ng-model=\"name\"]")).sendKeys(Keys.RETURN);
    }

    @Then("^the playlist \"([^\"]*)\" is added$")
    public void thePlaylistIsAdded(String name) throws Throwable {
        new WebDriverWait(driver, TIMEOUT).until((ExpectedCondition) input -> driver.findElement(className("playlists")).findElement(tagName("tbody")).findElements(tagName("tr")).size() > 0);
        List<WebElement> playlists = driver.findElements(cssSelector(".playlists tbody tr"));
        assertTrue(playlists.stream().anyMatch(p -> p.findElement(cssSelector("td:first-child")).getText().equals(name)));
    }

    @When("^the delete playlist button is clicked$")
    public void theDeletePlaylistButtonIsClicked() throws Throwable {
        playlistCounter = driver.findElement(className("playlists")).findElement(tagName("tbody")).findElements(tagName("tr")).size();
        driver.findElement(cssSelector("button[ng-click=\"deletePlaylist($event, list)\"]")).click();
    }

    @Then("^the playlist is deleted$")
    public void thePlaylistIsDeleted() throws Throwable {
        assertEquals(playlistCounter - 1, driver.findElement(className("playlists")).findElement(tagName("tbody")).findElements(tagName("tr")).size());
    }
}