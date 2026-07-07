package se.lexicon.skalmansfoodsleepclock.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.lexicon.skalmansfoodsleepclock.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsService;

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication manager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        // Build the AuthenticationManager directly (no .and())
        return authBuilder.build();
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/assets/**",

                                // Swagger
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",

                                // Public APIs
                                "/auth/**",
                                "/actuator/health",
                                "/meals/**",
                                "/reminders/**",
                                "/tasks/**",
                                "/chat/**",
                                "/users/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                );

        return http.build();
    }


    // Web MVC Configurer for React routing + CORS
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            // CORS config
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",           // local frontend
                                "https://skalmansfoodsleepclock.onrender.com" // deployed frontend
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            // React router fallback excluding Swagger / API docs
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/{spring:[^\\.]*}")
                        .setViewName("forward:/index.html");
                registry.addViewController("/**/{spring:[^\\.]*}")
                        .setViewName("forward:/index.html");
            }
        };
    }

    // Swagger / OpenAPI config
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Skalman Clock API")
                        .version("1.0")
                        .description("API for Skalman's Food Sleep Clock"));
    }
}







