package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Thomas on 2017-11-04.
 */
public class WodIT {

    private static String WORKOUT_RESOURCE = "users/wods";

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations={"/db/equipments.json", "/db/exercises.json", "/db/wods.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetWodById() {
        final String wodId = "59e24b59ca989685da26e4a1";
        get(WORKOUT_RESOURCE, wodId)
                .statusCode(200)
                .body("id", is(wodId))
                //TODO Fix dates and timezones
//                .body("date", is("2017-06-07T18:30:00+02:00"))
                .body("workouts[0].id", is("59e24b59ca989685da26e4a2"))
                .body("workouts[0].job.work", is(60))
                .body("workouts[0].job.sets", is(14))
                .body("workouts[0].alternating", is(true))
                .body("workouts[0].routines[0].name", is("1"))
                .body("workouts[0].routines[0].sessions[0].id", is("59e24b59ca989685da26e4a3"))
                .body("workouts[0].routines[0].sessions[0].exerciseId", is("59e24b59ca989685da26e4a4"))
                .body("workouts[0].routines[0].sessions[0].repsFrom", is(2))
                .body("workouts[0].routines[0].sessions[0].repsTo", is(2))
                .body("workouts[0].routines[0].sessions[1].id", is("59e24b59ca989685da26e4a5"))
                .body("workouts[0].routines[0].sessions[1].exerciseId", is("59e24b59ca989685da26e4a6"))
                .body("workouts[0].routines[0].sessions[1].repsFrom", is(2))
                .body("workouts[0].routines[0].sessions[1].repsTo", is(2));
    }

    @Test
    @UsingDataSet(locations={"/db/equipments.json", "/db/exercises.json", "/db/wods.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)

    public void shouldGetAllWodsForCurrentLoggedInUser() {
        get(WORKOUT_RESOURCE).statusCode(200).body("$.size()", is(1));
    }

    @Test
    @UsingDataSet(locations={"/db/equipments.json", "/db/exercises.json", "/db/wods.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldDeleteSingleWorkout() {
        final String id = "59e24b59ca989685da26e4b7";
        delete(WORKOUT_RESOURCE, id)
                .statusCode(204);

        get(WORKOUT_RESOURCE, id)
                .statusCode(404);
    }

    @Test
    @UsingDataSet(loadStrategy= LoadStrategyEnum.DELETE_ALL)
    public void shouldFetchNonExistingWorkout() {
        get(WORKOUT_RESOURCE, "ABC")
                .statusCode(404);
    }

    @Test
    @UsingDataSet(loadStrategy= LoadStrategyEnum.DELETE_ALL)
    public void shouldFetchAllExistingWorkouts() {
        get(WORKOUT_RESOURCE, "ABC")
                .statusCode(404);
    }

    private static ValidatableResponse delete(String resource, String id) {
        return given()
                .delete("{resource}/{id}", resource, id)
                .then();
    }

    private static ValidatableResponse get(String resource, String id) {
        return given()
                .accept(ContentType.JSON)
                .get("{resource}/{id}", resource, id)
                .then();
    }

    private static ValidatableResponse get(String resource) {
        return get(resource, "");
    }

    @Test @Ignore
    public void test() {
        for (int i = 0; i < 10; i++) {
            ObjectId y = new ObjectId();
            System.out.println(y);

        }
    }
}
