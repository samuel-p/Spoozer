package de.saphijaga.spoozer.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by samuel on 05.10.15.
 */
@Controller
public class SpoozerController {
    @RequestMapping("/")
    public String getWebContent() {
        return "index";
    }

    @RequestMapping("/app/**")
    public String getWebContentForApp() {
        return "index";
    }
}