package se.lexicon.skalmansfoodsleepclock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/health")
    public String home() {
        return "Backend is running 🚀";
    }
}
