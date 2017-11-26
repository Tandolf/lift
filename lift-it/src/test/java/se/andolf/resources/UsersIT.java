package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.api.user.ContactType;
import se.andolf.api.user.Meta;
import se.andolf.api.user.Name;
import se.andolf.api.user.User;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import java.time.LocalDateTime;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author Thomas on 2017-10-28.
 */
public class UsersIT {

    private static final String RESOURCE = "users";
    private static final String USER_ID = "59e24b59ca989685da26e4e1";

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations="/db/user.json", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetValidUser() {
        given()
                .contentType(ContentType.JSON)
                .get("{resource}/{id}", RESOURCE, USER_ID)
                .then()
                .statusCode(200)
                .body("id", is(USER_ID))
                .body("meta.created", is(notNullValue()))
                .body("meta.lastModified", is(notNullValue()))
                .body("timezone", is("Europe/Stockholm"))
                .body("userName", is("john.doe@gmail.com"))
                .body("name.formatted", is("John Doe"))
                .body("name.givenName", is("John"))
                .body("name.familyName", is("Doe"))
                .body("emails[0].type", is(ContactType.PRIVATE.name()))
                .body("emails[0].value", is("john.doe@gmail.com"))
                .body("emails[0].primary", is(true))
                .body("addresses[0].type", is(ContactType.HOME.name()))
                .body("addresses[0].streetAddress", is("Torsgatan 1"))
                .body("addresses[0].locality", is("Stockholm"))
                .body("addresses[0].postalCode", is("11400"))
                .body("addresses[0].country", is("Sweden"))
                .body("addresses[0].primary", is(true))
                .body("phoneNumbers[0].type", is(ContactType.MOBILE.name()))
                .body("phoneNumbers[0].value", is("0046701234567"))
                .body("active", is(true));
    }

    @Test
    @UsingDataSet(loadStrategy=LoadStrategyEnum.DELETE_ALL)
    public void shouldAddSingleUser() {

        final LocalDateTime now = LocalDateTime.now();
        final String givenName = "Jane";
        final String familyName = "Doe";

        final Meta meta = new Meta.Builder().lastModified(now).created(now).build();
        final Name name = new Name.Builder()
                .givenName(givenName)
                .familyName(familyName)
                .build();

        final User user = new User.Builder()
                .setUserName("jane.doe@gmail.com")
                .isActive(true)
                .meta(meta)
                .name(name)
                .password("password")
                .build();

        final String location = given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(RESOURCE)
                .then()
                .statusCode(201)
                .extract().header("location");

        get(location)
                .then()
                .statusCode(200)
                .body("userName", is("jane.doe@gmail.com"))
                .body("active", is(true))
//                .body("meta.created", is(now.toString()))
//                .body("meta.lastModified", is(now.toString()))
                .body("name.givenName", is(givenName))
                .body("name.familyName", is(familyName))
                .body("roles[0].value", is("USER"));
    }
}
