package de.saphijaga.spoozer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by samuel on 05.10.15.
 */
@Configuration
@EnableWebMvc
@ComponentScan("de.saphijaga.spoozer")
public class SpoozerConfig {
}