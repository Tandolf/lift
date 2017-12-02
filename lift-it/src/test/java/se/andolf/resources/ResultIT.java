package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.api.Result;
import se.andolf.api.workout.ExerciseParam;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

/**
 * @author Thomas on 2017-12-02.
 */
public class ResultIT {

    private static String RESULT_RESOURCE = "users/workouts/{workoutid}/results";

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations={"/db/equipments.json", "/db/exercises.json", "/db/workouts.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldPostResultToWorkout(){
        final Map<ExerciseParam, Object> values = new HashMap<>();
        values.put(ExerciseParam.WEIGHT, 75);

        final Result result = new Result.Builder()
                .date(LocalDateTime.now())
                .sets(Collections.singletonList(values))
                .build();

        final String location = given()
                .contentType(ContentType.JSON)
                .body(result)
                .post(RESULT_RESOURCE, "59e24b59ca989685da26e4e2")
                .then()
                .statusCode(201)
                .extract()
                .header("location");

        get(location)
                .then()
                .assertThat()
                .statusCode(200);

    }
}
