package se.andolf.configuration;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author Thomas on 2016-06-25.
 */
@Configuration
@ComponentScan(basePackages = "se.andolf.controller")
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(paths())
                    .build()
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDate.class, String.class)
                .ignoredParameterTypes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .tags(new Tag("Categories", ""),
                        new Tag("Equipments", ""),
                        new Tag("Exercises", ""),
                        new Tag("Workouts", "")
                );
    }

    private Predicate<String> paths() {

        return or(
                regex("/exercises.*"),
                regex("/categories.*"),
                regex("/equipments.*"),
                regex("/workouts.*")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Lift API")
                .description("This is the API for the Lift application. Its an application that has the ability to log " +
                        "crossfit workouts so that you can keep track of your progress. ")
                .contact(new Contact("Tandolf", "http://www.github.com/lift", "thomas.andolf@gmail.com"))
                .version("1.0")
                .build();
    }
}
