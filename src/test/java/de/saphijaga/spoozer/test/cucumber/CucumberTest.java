package de.saphijaga.spoozer.test.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", glue = "de.saphijaga.spoozer.test.cucumber.steps", features = "classpath:features")
public class CucumberTest {
}