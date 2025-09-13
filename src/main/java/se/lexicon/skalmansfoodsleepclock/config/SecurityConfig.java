package se.lexicon.skalmansfoodsleepclock.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.lexicon.skalmansfoodsleepclock.service.UserDetailsServiceImp;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // Enable CORS using WebMvcConfigurer
                .authorizeHttpRequests(auth -> auth
                        // Allow React static resources, API endpoints, and health check
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/assets/**",           // Vite assets
                                "/manifest.webmanifest",
                                "/favicon.ico",
                                "/auth/**",
                                "/actuator/health"
                        ).permitAll()
                        .anyRequest().authenticated() // All other endpoints require authentication
                );
        return http.build();
    }

    // Global CORS and React routing fallback
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            // CORS configuration
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",           // local React dev
                                "https://skalmansfoodsleepclock.onrender.com" // deployed frontend
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // must not use "*"
            }

            // React router fallback
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/{spring:[a-zA-Z0-9-_]+}")
                        .setViewName("forward:/index.html");
                registry.addViewController("/**/{spring:[a-zA-Z0-9-_]+}")
                        .setViewName("forward:/index.html");
            }
        };
    }
}
