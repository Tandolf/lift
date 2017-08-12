package se.andolf.resources;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.api.Exercise;
import se.andolf.config.Client;
import se.andolf.util.FileUtils;
import se.andolf.util.UriUtil;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static se.andolf.util.DbUtil.purge;

/**
 * @author Thomas on 2017-06-18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class WorkoutResourceIT {

    private static final String EXERCISE_RESOURCE = "exercises";
    private static final String USER_RESOURCE = "users";
    private static final String WORKOUT_RESOURCE = "workouts";

    private String userWorkoutPath;

    @BeforeClass
    public static void init(){
        Client.init();
    }

    @Before
    public void purgeDB() {
        purge(USER_RESOURCE);
        final String userId = UriUtil.extractLastPath(UserResourceIT.putUser(FileUtils.read("users/user.json")));
        userWorkoutPath = USER_RESOURCE + "/" + userId + "/" + WORKOUT_RESOURCE;
        purge(userWorkoutPath);
        purge(EXERCISE_RESOURCE);
    }

    @Test
    public void shouldSaveTwoDifferentWorkoutsOnTheSameDay(){
        final List<Integer> exercises = putExercises("Hang Power Clean", "Push Press", "Power Clean", "Split Jerk");
        final List<Integer> exercises2 = putExercises("Back Squats", "Half TGU");
        final String id = put(formatJson("170607_1.json", exercises));
        final String id2 = put(formatJson("170607_2.json", exercises2));

        given()
                .get("/{resource}/{id}", userWorkoutPath, id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(Integer.parseInt(id)));

        given()
                .get("/{resource}/{id}", userWorkoutPath, id2)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(Integer.parseInt(id2)));
    }

    @Test
    public void shouldSaveWorkoutWithMultipleGroups(){
        final List<Integer> exercises = putExercises("Sledge slams", "Target sprawls", "Assault bike", "Row", "Walking lunges", "Shuttle runs");
        final String id = put(formatJson("170502.json", exercises));

        given()
                .get("/{resource}/{id}", userWorkoutPath, id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(Integer.parseInt(id)));
    }

    @Test
    public void shouldFetchWorkoutWithMultipleGroupsInCorrectOrderOfGroupsAndExercises(){
        final List<Integer> exercises = putExercises(
                "Heavy farmers walk",
                "Shuttle runs",
                "Toes to bar",
                "Assault bike",
                "Goblet reverse lunge from block",
                "Row",
                "Dumbbell Thrusters",
                "Target burpees"
        );
        final String id = put(formatJson("170706.json", exercises));

        given()
                .get("/{resource}/{id}", userWorkoutPath, id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("exerciseSessions[0].workoutType", is("AMRAP"))
                .body("exerciseSessions.exerciseSessions[0][0].exerciseId", is(exercises.get(0)))
                .body("exerciseSessions.exerciseSessions[0][1].exerciseId", is(exercises.get(1)))
                .body("exerciseSessions[1].workoutType", is("AMRAP"))
                .body("exerciseSessions.exerciseSessions[1][0].exerciseId", is(exercises.get(2)))
                .body("exerciseSessions.exerciseSessions[1][1].exerciseId", is(exercises.get(3)))
                .body("exerciseSessions[2].workoutType", is("AMRAP"))
                .body("exerciseSessions.exerciseSessions[2][0].exerciseId", is(exercises.get(4)))
                .body("exerciseSessions.exerciseSessions[2][1].exerciseId", is(exercises.get(5)))
                .body("exerciseSessions[3].workoutType", is("AMRAP"))
                .body("exerciseSessions.exerciseSessions[3][0].exerciseId", is(exercises.get(6)))
                .body("exerciseSessions.exerciseSessions[3][1].exerciseId", is(exercises.get(7)));
    }

    @Test
    public void shouldSaveWorkoutWithStraplessAndDamperParam(){
        final List<Integer> exercises = putExercises(
                "Heavy farmers walk",
                "Shuttle runs",
                "Toes to bar",
                "Assault bike",
                "Goblet reverse lunge from block",
                "Row",
                "Dumbbell Thrusters",
                "Target burpees"
        );
        final String id = put(formatJson("170706.json", exercises));

        given()
                .get("/{resource}/{id}", userWorkoutPath, id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(Integer.parseInt(id)))
                .body("work", equalTo(180))
                .body("rest", equalTo(60))
                .body("sets", equalTo(8));
    }

    @Test
    public void shouldDeleteASingleWorkout() {
        final List<Integer> exercises = putExercises("Hang Power Clean", "Push Press", "Power Clean", "Split Jerk");
        final String id = put(formatJson("170607_1.json", exercises));
        given()
                .delete("{resource}/{id}", userWorkoutPath, id)
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldReturnAListOfWorkoutsWithIdAndDate() {
        final List<Integer> exercises = putExercises(
                "Hang Power Clean",
                "Push Press",
                "Power Clean",
                "Split Jerk");
        put(formatJson("170607_1.json", exercises));
        put(formatJson("170607_1.json", exercises));
        put(formatJson("170607_1.json", exercises));
        get(userWorkoutPath)
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", equalTo(3))
                .body("id", hasItem(CoreMatchers.notNullValue()))
                .body("date", hasItem(notNullValue()));
    }

    @Test
    public void shouldReturnAListOfWorkoutsFilteredByDate() {
        final List<Integer> exercises = putExercises("Hang Power Clean", "Push Press", "Power Clean", "Split Jerk");
        final List<Integer> exercises2 = putExercises("Back Squats", "Half TGU");
        final List<Integer> exercises3 = putExercises(
                "Heavy farmers walk",
                "Shuttle runs",
                "Toes to bar",
                "Assault bike",
                "Goblet reverse lunge from block",
                "Row",
                "Dumbbell Thrusters",
                "Target burpees"
        );
        final int id = Integer.parseInt(put(formatJson("170607_1.json", exercises)));
        final int id2 = Integer.parseInt(put(formatJson("170607_2.json", exercises2)));
        put(formatJson("170706.json", exercises3));

        given()
                .queryParam("date", "2017-06-07")
                .get("/{resource}", userWorkoutPath)
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(equalTo(2)))
                .body("id", containsInAnyOrder(id, id2));
    }

    @Test
    public void shouldReturn404IfFetchedWorkoutDoesNotExists() {
        final Long id = 1234L;
        get("/{resource}/{id}", userWorkoutPath, id)
                .then()
                .assertThat()
                .statusCode(404);
    }

    private List<Integer> putExercises(String... equipmentNames){
        final List<Integer> exerciseIds = new ArrayList<>();
        for(String name : equipmentNames) {
            exerciseIds.add(Integer.parseInt(put(new Exercise.Builder().setName(name).build())));
        }
        return exerciseIds;
    }

    public static String put(Exercise exercise) {
        try {
            final String header = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).body(exercise).put(EXERCISE_RESOURCE).then().assertThat().statusCode(201).extract().header("Location");
            return UriUtil.extractLastPath(header);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private static String formatJson(String filename, List<Integer> exerciseIds) {
        final String jsonTemplate = FileUtils.read(filename);
        final Integer[] exerciseIdArray = exerciseIds.toArray(new Integer[exerciseIds.size()]);
        return String.format(jsonTemplate, (Object[])exerciseIdArray);
    }

    private String put(String json) {
        return UriUtil.extractLastPath(given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(json)
                .when()
                .put(userWorkoutPath)
                .then()
                .assertThat()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location"));
    }
}
