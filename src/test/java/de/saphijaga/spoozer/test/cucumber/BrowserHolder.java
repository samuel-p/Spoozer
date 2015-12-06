package de.saphijaga.spoozer.test.cucumber;

import org.openqa.selenium.WebDriver;

/**
 * Created by samuel on 02.12.15.
 */
public class BrowserHolder {
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}