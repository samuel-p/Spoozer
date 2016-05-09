package de.saphijaga.spoozer.test.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", glue = "de.saphijaga.spoozer.test.cucumber.steps", features = "classpath:features/")
public class CucumberTest {
}