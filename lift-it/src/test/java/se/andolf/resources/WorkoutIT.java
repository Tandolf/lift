package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.api.workout.Job;
import se.andolf.api.workout.WorkoutType;
import se.andolf.api.workout.ExerciseParam;
import se.andolf.api.workout.Work;
import se.andolf.api.workout.Workout;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import java.time.LocalDateTime;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author Thomas on 2017-11-04.
 */
public class WorkoutIT {

    private static String WORKOUT_RESOURCE = "users/workouts";

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations={"/db/equipments.json", "/db/exercises.json", "/db/workouts.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetWodById() {
        final String wodId = "59e24b59ca989685da26e4e2";
        get(WORKOUT_RESOURCE, wodId)
                .statusCode(200)
                .body("id", is(notNullValue()))
                .body("date", is(notNullValue()))
                .body("user", is("59e24b59ca989685da26e4e1"))
                .body("job.work", is(120))
                .body("job.sets", is(6))
                .body("job.rest", is(30))
                .body("job.workoutType", is(WorkoutType.AMRAP.name()))
                .body("description", is("someDescription"))
                .body("jobs.size()", is(6));
    }

    @Test
    @UsingDataSet(locations={"/db/equipments.json", "/db/exercises.json", "/db/workouts.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldGetAllWodsForCurrentLoggedInUser() {
        get(WORKOUT_RESOURCE).statusCode(200).body("$.size()", is(1));
    }

    @Test
    @UsingDataSet(locations={"/db/equipments.json", "/db/exercises.json", "/db/workouts.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
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

    @Test
    @UsingDataSet(loadStrategy= LoadStrategyEnum.DELETE_ALL)
    public void shouldAddWorkout() {
        final Work work = new Work.Builder().exerciseId("1").put(ExerciseParam.FOR_CAL, true).put(ExerciseParam.STRAPS, true).put(ExerciseParam.DAMPER, 4).build();
        final Work work2 = new Work.Builder().exerciseId("2").build();
        final Work work3 = new Work.Builder().exerciseId("3").put(ExerciseParam.REPS, 8).put(ExerciseParam.WEIGHT, 24).build();
        final Work work4 = new Work.Builder().exerciseId("4").put(ExerciseParam.REPS, 6).put(ExerciseParam.WEIGHT, 24).build();
        final Work work5 = new Work.Builder().exerciseId("5").put(ExerciseParam.REPS, 4).put(ExerciseParam.WEIGHT, 24).build();
        final Work work6 = new Work.Builder().exerciseId("6").put(ExerciseParam.REPS, 10).build();
        final Work work7 = new Work.Builder().exerciseId("7").put(ExerciseParam.REPS, 9).build();
        final Work work8 = new Work.Builder().exerciseId("8").put(ExerciseParam.DISTANCE, 10).build();
        final Work work9 = new Work.Builder().exerciseId("9").put(ExerciseParam.DISTANCE, 10).build();
        final Work work10 = new Work.Builder().exerciseId("9").put(ExerciseParam.FOR_CAL, true).build();

        final Workout workout = new Workout.Builder()
                .user("someUser")
                .date(LocalDateTime.now())
                .effort(90)
                .job(new Job(120, 30, 6, WorkoutType.AMRAP))
                .description("someDescription")
                .addWork(work)
                .addWork(work2)
                .addWork(work3, work4, work5)
                .addWork(work6, work7)
                .addWork(work8, work9)
                .addWork(work10)
                .build();

        final String location = given()
                .contentType(ContentType.JSON)
                .body(workout)
                .post(WORKOUT_RESOURCE)
                .then()
                .assertThat()
                .statusCode(201)
                .extract().header("location");

        given().get(location).then().statusCode(200).extract().response().prettyPrint();
    }

    @Test
    public void shouldAddWorkoutWithTestParameter() {
        final Work work = new Work.Builder()
                .put(ExerciseParam.TEST, true)
                .build();

        final Workout workout = new Workout.Builder()
                .addWork(work)
                .build();

        final String location = given()
                .contentType(ContentType.JSON)
                .body(workout)
                .post(WORKOUT_RESOURCE)
                .then()
                .assertThat()
                .statusCode(201)
                .extract().header("location");

        given().get(location).then().statusCode(200)
        .body("jobs[0][0].parameters.TEST", is(true));

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
