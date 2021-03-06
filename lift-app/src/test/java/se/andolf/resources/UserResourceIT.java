package se.andolf.resources;

import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.config.Client;
import se.andolf.util.FileUtils;
import se.andolf.util.UriUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static se.andolf.util.DbUtil.purge;

/**
 * @author Thomas on 2017-07-16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Ignore
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
        final String header = putUser(user);
        delete(header).then().assertThat().statusCode(204);
    }

    @Test
    public void shouldRetrieveAListOfUserIds() {
        final int id = Integer.parseInt(UriUtil.extractLastPath(putUser(FileUtils.read("users/user.json"))));
        final int id2 = Integer.parseInt(UriUtil.extractLastPath(putUser(FileUtils.read("users/user2.json"))));
        final int id3 = Integer.parseInt(UriUtil.extractLastPath(putUser(FileUtils.read("users/user3.json"))));

        get(USER_RESOURCE)
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", equalTo(3))
                .body("id", containsInAnyOrder(id, id2, id3));

    }

    @Test
    public void shouldThrowConflict409IfTryingToAddUsersWithSameEmail() {
        putUser(FileUtils.read("users/user.json"));
        given()
                .contentType(ContentType.JSON)
                .body(FileUtils.read("users/user.json"))
                .put(USER_RESOURCE)
                .then()
                .assertThat()
                .statusCode(409);
    }

    @Test
    public void shouldRetrieveUserWithCorrectInformation() {
        final String location = putUser(FileUtils.read("users/user.json"));
        get(location)
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", is(equalTo("Kalle")))
                .body("lastname", is(equalTo("Andersson")))
                .body("accountInfo.email", is(equalTo("kalle.andersson@gmail.com")))
                .body("contactInfo.addressLine1", is(equalTo("Sveavägen 15")))
                .body("contactInfo.addressLine2", is(equalTo("2tr")))
                .body("contactInfo.city", is(equalTo("Stockholm")))
                .body("contactInfo.stateOrProvince", is(equalTo("Stockholms län")))
                .body("contactInfo.postalCode", is(equalTo("11422")))
                .body("contactInfo.mobileNumber", is(equalTo("0046701234567")));

    }

    public static String putUser(String json) {
        return given()
                .contentType(ContentType.JSON)
                .body(json)
                .put("{path}", USER_RESOURCE)
                .then()
                .assertThat()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");
    }
}
