package de.saphijaga.spoozer.test.application.config;

import de.saphijaga.spoozer.test.cucumber.BrowserHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by samuel on 06.12.15.
 */
@Configuration
public class TestCucumberConfig {
    @Bean
    public BrowserHolder browserHolder() {
        return new BrowserHolder();
    }

    @Bean
    public TestInitialization testInitialization() {
        return new TestInitialization();
    }
}