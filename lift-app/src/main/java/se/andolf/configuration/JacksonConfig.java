package se.andolf.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.andolf.json.JacksonObjectMapper;

/**
 * @author by thomas.andolf
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonObjectMapper.INSTANCE.objectMapper();
    }

}
