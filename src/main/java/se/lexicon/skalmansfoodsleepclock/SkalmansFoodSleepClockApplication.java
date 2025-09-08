package se.lexicon.skalmansfoodsleepclock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SkalmansFoodSleepClockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkalmansFoodSleepClockApplication.class, args);
    }

}
// This component sets the server port dynamically
@Component
class ServerPortCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${PORT:8080}")  // If PORT not set, fallback to 8080
    private int port;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(port);
    }
}