package de.saphijaga.spoozer.web.controller;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by samuel on 16.10.15.
 */
public class MockMvcIntegration {
    public static InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
}