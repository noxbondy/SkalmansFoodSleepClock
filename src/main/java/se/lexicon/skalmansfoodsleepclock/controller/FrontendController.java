package se.lexicon.skalmansfoodsleepclock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    // Catch-all for React routes
    @GetMapping(value = { "/", "/user", "/dashboard/**", "/meal/**", "/reminder/**" })
    public String index() {
        return "index.html"; // serves React build
    }
}