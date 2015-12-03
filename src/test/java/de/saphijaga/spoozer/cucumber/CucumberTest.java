package de.saphijaga.spoozer.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", glue = "de.saphijaga.spoozer.cucumber.steps", features = "classpath:features/search-songs.feature")
public class CucumberTest {
}