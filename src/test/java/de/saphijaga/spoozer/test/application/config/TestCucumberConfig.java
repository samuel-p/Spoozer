package de.saphijaga.spoozer.test.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by samuel on 06.12.15.
 */
@Configuration
public class TestCucumberConfig {
    @Bean
    public TestInitialization testInitialization() {
        return new TestInitialization();
    }
}