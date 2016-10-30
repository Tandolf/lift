package se.andolf.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.util.DatabaseTestUtils;
import se.andolf.util.TestData;

import static com.jayway.restassured.RestAssured.given;

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

    @Autowired
    private DatabaseTestUtils databaseTestUtils;

    @Before
    public void init(){
        databaseTestUtils.clearAll();
        databaseTestUtils.createDb();
    }

    @Test
    public void should_add_a_user_to_the_db(){

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(TestData.USER.NEW.getRestUser())
        .when()
                .put(PATH_UNDER_TEST)
        .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", is(notNullValue()));
    }

    @Test
    public void should_return_not_found(){

        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .delete("/user/123")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
