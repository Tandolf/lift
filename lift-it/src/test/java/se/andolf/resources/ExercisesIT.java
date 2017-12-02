package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.api.Exercise;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @author Thomas on 2017-10-28.
 */
public class ExercisesIT {

    private static final String RESOURCE = "exercises";
    private static final String EXERCISE_NO_EQUIPMENT_ID = "59e24b59ca989685da26e4f3";
    private static final String EXERCISE_WITH_EQUIPMENT_ID = "59e24b59ca989685da26e4f1";

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations="/db/exercises.json", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetExerciseById() {
        given()
                .contentType(ContentType.JSON)
                .get("{resource}/{id}", RESOURCE, EXERCISE_NO_EQUIPMENT_ID)
                .then()
                .statusCode(200)
                .body("id", is(EXERCISE_NO_EQUIPMENT_ID))
                .body("name", is("HSPU"));
    }

    @Test
    @UsingDataSet(locations={"/db/equipment.json", "/db/exercises.json"}, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetExerciseByIdThatContainsEquipment() {
        given()
                .contentType(ContentType.JSON)
                .get("{resource}/{id}", RESOURCE, EXERCISE_WITH_EQUIPMENT_ID)
                .then()
                .statusCode(200)
                .body("id", is(EXERCISE_WITH_EQUIPMENT_ID))
                .body("name", is("Kettlebell Swings"))
                .body("equipments[0]", is("59e24b59ca989685da26e4e3"));
    }

    @Test
    @UsingDataSet(locations={"/db/exercises.json"}, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetAllExercises() {
        given()
                .contentType(ContentType.JSON)
                .get(RESOURCE)
                .then()
                .statusCode(200)
                .body("$.size()", is(20));
    }

    @Test
    @UsingDataSet(locations={"/db/exercises.json"}, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void shouldDeleteOneExercises() {
        given()
                .delete("{resource}/{id}", RESOURCE, EXERCISE_WITH_EQUIPMENT_ID)
                .then()
                .statusCode(204);

        get(RESOURCE)
                .then()
                .statusCode(200)
                .body("$.size()", is(19));
    }

    @Test
    @UsingDataSet(loadStrategy=LoadStrategyEnum.DELETE_ALL)
    public void shouldSaveNewExerciseWithoutEquipment() {
        final String location = given()
                .contentType(ContentType.JSON)
                .body(new Exercise.Builder().setName("Push ups").build())
                .post(RESOURCE)
                .then()
                .statusCode(201)
                .extract().header("location");
        get(location)
                .then()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .body("name", is("Push ups"));
    }

    @Test
    @UsingDataSet(locations={"/db/exercises.json"}, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void shouldFetchExercisesFilteredOnName() {
        given()
                .param("name", "clean")
                .get(RESOURCE)
                .then()
                .statusCode(200)
                .body("$.size()", is(equalTo(2)));
    }
}
