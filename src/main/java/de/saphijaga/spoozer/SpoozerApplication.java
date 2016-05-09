package de.saphijaga.spoozer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by samuel on 05.10.15.
 */
@SpringBootApplication
@ComponentScan("de.saphijaga.spoozer")
public class SpoozerApplication {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(SpoozerApplication.class, args)) {
            context.registerShutdownHook();
        }
    }
}