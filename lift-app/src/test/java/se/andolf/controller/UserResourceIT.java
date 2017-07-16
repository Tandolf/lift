package se.andolf.controller;

import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.config.Client;
import se.andolf.util.FileUtils;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static se.andolf.util.DbUtil.purge;

/**
 * @author Thomas on 2017-07-16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class UserResourceIT {

    private static final String USER_RESOURCE = "users";

    @BeforeClass
    public static void init(){
        Client.init();
    }

    @Before
    public void purgeDB() {
        purge(USER_RESOURCE);
    }

    @Test
    public void shouldDeleteUserById(){
        final String user = FileUtils.read("users/user.json");

        final String header = given()
                .contentType(ContentType.JSON)
                .body(user)
                .put("{path}/", USER_RESOURCE)
                .then()
                .assertThat()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        delete(header).then().assertThat().statusCode(204);
    }
}
