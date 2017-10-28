package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

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
    @UsingDataSet(locations={"/db/user.json", "/db/categories.json"}, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetValidUser() {
        given()
                .contentType(ContentType.JSON)
                .get("{resource}/{id}", RESOURCE, USER_ID)
                .then()
                .statusCode(200)
                .body("id", is(USER_ID))
                .body("meta.created", is("2011-08-01T20:29:49.793"))
                .body("meta.lastModified", is("2011-08-01T20:29:49.793"))
                .body("meta.location", is("http://localhost:8080/Users/59e24b59ca989685da26e4e1"))
                .body("userName", is("john.doe@gmail.com"))
                .body("name.formatted", is("John Doe"))
                .body("name.givenName", is("John"))
                .body("name.familyName", is("Doe"))
                .body("emails[0].type", is("private"))
                .body("emails[0].value", is("john.doe@gmail.com"))
                .body("emails[0].primary", is(true))
                .body("phoneNumbers[0].type", is("mobile"))
                .body("phoneNumbers[0].value", is("0046701234567"))
                .body("active", is(true));

    }
}
