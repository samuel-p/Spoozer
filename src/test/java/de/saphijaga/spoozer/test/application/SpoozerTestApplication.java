package de.saphijaga.spoozer.test.application;

import de.saphijaga.spoozer.config.*;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.test.application.config.TestCucumberConfig;
import de.saphijaga.spoozer.test.application.config.TestMongoConfig;
import de.saphijaga.spoozer.test.application.config.TestSpoozerConfig;
import de.saphijaga.spoozer.test.application.config.TestSslConfig;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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