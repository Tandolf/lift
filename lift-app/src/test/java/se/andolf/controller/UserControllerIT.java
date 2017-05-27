package se.andolf.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.util.TestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by Thomas on 2016-07-17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class UserControllerIT {

    private static String PATH_UNDER_TEST = "/user";

    @Before
    public void init(){

    }

    @Test @Ignore
    public void should_add_a_user_to_the_db(){

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(TestData.USER.NEW.getRestUser())
        .when()
                .put(PATH_UNDER_TEST)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", is(notNullValue()));
    }

    @Test @Ignore
    public void should_return_not_found(){

        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .delete("/user/123")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
