package se.andolf.controller;

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
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static se.andolf.util.DbUtil.purge;

/**
 * @author Thomas on 2017-06-18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class WorkoutControllerIT {

    private static final String EXERCISE_RESOURCE = "exercises";
    private static final String WORKOUT_RESOURCE = "workouts";

    @BeforeClass
    public static void init(){
        Client.init();
    }

    @Before
    public void purgeDB() {
        purge(WORKOUT_RESOURCE);
        purge(EXERCISE_RESOURCE);
    }

    @Test
    public void shouldSaveTwoDifferentWorkoutsOnTheSameDay(){
        final List<Integer> exercises = putExercises("Hang Power Clean", "Push Press", "Power Clean", "Split Jerk");
        final List<Integer> exercises2 = putExercises("Back Squats", "Half TGU");
        final String id = put(formatJson("170607_1.json", exercises));
        final String id2 = put(formatJson("170607_2.json", exercises2));

        given()
                .get("/{resource}/{id}", WORKOUT_RESOURCE, id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(Integer.parseInt(id)));

        given()
                .get("/{resource}/{id}", WORKOUT_RESOURCE, id2)
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
                .get("/{resource}/{id}", WORKOUT_RESOURCE, id)
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
                .get("/{resource}/{id}", WORKOUT_RESOURCE, id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("exercises[0].type", is("AMRAP"))
                .body("exercises.exercises[0][0].id", is(exercises.get(0)))
                .body("exercises.exercises[0][1].id", is(exercises.get(1)))
                .body("exercises[1].type", is("AMRAP"))
                .body("exercises.exercises[1][0].id", is(exercises.get(2)))
                .body("exercises.exercises[1][1].id", is(exercises.get(3)))
                .body("exercises[2].type", is("AMRAP"))
                .body("exercises.exercises[2][0].id", is(exercises.get(4)))
                .body("exercises.exercises[2][1].id", is(exercises.get(5)))
                .body("exercises[3].type", is("AMRAP"))
                .body("exercises.exercises[3][0].id", is(exercises.get(6)))
                .body("exercises.exercises[3][1].id", is(exercises.get(7)));
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
                .get("/{resource}/{id}", WORKOUT_RESOURCE, id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(Integer.parseInt(id)))
                .body("work", equalTo(180))
                .body("rest", equalTo(60))
                .body("sets", equalTo(8))
                .body("resistances.damper", hasItems(3))
                .body("resistances.strapless", hasItems(true));
    }

    @Test
    public void shouldDeleteASingleWorkout() {
        final List<Integer> exercises = putExercises("Hang Power Clean", "Push Press", "Power Clean", "Split Jerk", "Back Squats", "Half TGU");
        final String id = put(formatJson("170607_1.json", exercises));
        given()
                .delete("{resource}/{id}", WORKOUT_RESOURCE, id)
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldReturnAListOfIds() {
        final List<Integer> exercises = putExercises("Hang Power Clean", "Push Press", "Power Clean", "Split Jerk");
        put(formatJson("170607_1.json", exercises));
        put(formatJson("170607_1.json", exercises));
        put(formatJson("170607_1.json", exercises));
        final String json = given().get("/workouts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().asString();
        final List<Long> ids = from(json).get("id");
        assertEquals(3, ids.size());
    }

    @Test
    public void shouldReturn404IfFetchedWorkoutDoesNotExists() {
        final Long id = 1234L;
        get("/{resource}/{id}", WORKOUT_RESOURCE, id)
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

    private static String put(String json) {
        return UriUtil.extractLastPath(given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(json)
                .when()
                .put(WORKOUT_RESOURCE)
                .then()
                .assertThat()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location"));
    }
}
