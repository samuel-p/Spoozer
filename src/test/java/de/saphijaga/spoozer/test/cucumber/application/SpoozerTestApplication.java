package de.saphijaga.spoozer.test.cucumber.application;

import de.saphijaga.spoozer.config.*;
import de.saphijaga.spoozer.test.cucumber.application.config.TestCucumberConfig;
import de.saphijaga.spoozer.test.cucumber.application.config.TestMongoConfig;
import de.saphijaga.spoozer.test.cucumber.application.config.TestSpoozerConfig;
import de.saphijaga.spoozer.test.cucumber.application.config.TestSslConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by samuel on 05.10.15.
 */
@SpringBootApplication
@Import({
        TestCucumberConfig.class,
        TestSpoozerConfig.class,
        TestMongoConfig.class,
        SecurityConfig.class,
        SessionConfig.class,
        TestSslConfig.class,
        WebMvcConfig.class,
        WebSocketConfig.class,
        WebSocketSecurityConfig.class
})
public class SpoozerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpoozerTestApplication.class, args);
    }
}