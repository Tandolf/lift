package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.api.Category;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Thomas on 2017-10-15.
 */
public class CategoriesIT {

    private static final String RESOURCE = "categories";

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations= "/db/categories.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturnListOfCategories(){

        given()
                .accept(ContentType.JSON)
                .when()
                .get(RESOURCE)
                .then()
                .assertThat()
                .statusCode(200)
                .body("$.size()", equalTo(3))
                .body("name", hasItems("Arms", "Legs", "Chest"));
    }

    @Test
    @UsingDataSet(locations= "/db/categories.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn404IfCategoryDoesNotExist(){
        given()
                .accept(ContentType.JSON)
                .when()
                .get("{path}/{id}", RESOURCE, 1)
                .then()
                .statusCode(404);
    }

    @Test
    @UsingDataSet(locations= "/db/categories.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn204IfDeletingAnNonExistingCategory(){

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{path}/{id}", RESOURCE, 1)
                .then()
                .statusCode(204);
    }

    @Test
    @UsingDataSet(locations= "/db/categories.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn409IfCategoryNameExists(){

        given()
                .contentType(ContentType.JSON)
                .body(new Category("Arms"))
                .when()
                .put(RESOURCE)
                .then()
                .statusCode(409);
    }

    @Test
    @UsingDataSet(locations= "/db/categories.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldSaveCategory(){

        final Category category = new Category("Cardio");

        final String location = given()
                .contentType(ContentType.JSON)
                .body(category)
                .when()
                .put(RESOURCE)
                .then()
                .assertThat()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        given()
                .accept(ContentType.JSON)
                .when()
                .get(location)
                .then()
                .statusCode(200)
                .body("name", Matchers.is("Cardio"));

    }

    @Test
    @UsingDataSet(locations= "/db/categories.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn200IfCategoryWasFetched(){

        given()
                .accept(ContentType.JSON)
                .when()
                .get("{path}/{id}", RESOURCE, "59e24b59ca989685da26e4e5")
                .then()
                .statusCode(200)
                .body("name", Matchers.is("Arms"));
    }

    @Test
    @UsingDataSet(locations= "/db/categories.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldDeleteAnExistingCategory(){

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{path}/{id}", RESOURCE, "59e24b59ca989685da26e4e5")
                .then()
                .statusCode(204);

        given()
                .accept(ContentType.JSON)
                .when()
                .get(RESOURCE)
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(2));
    }
}
