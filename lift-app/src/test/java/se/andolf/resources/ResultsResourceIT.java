package se.andolf.resources;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import se.andolf.api.Exercise;
import se.andolf.config.Client;
import se.andolf.util.FileUtils;
import se.andolf.util.UriUtil;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static se.andolf.util.DbUtil.purge;

/**
 * @author Thomas on 2017-08-05.
 */
public class ResultsResourceIT {

    private static final String EXERCISE_RESOURCE = "exercises";
    private static final String USER_RESOURCE = "users";
    private static final String WORKOUT_RESOURCE = "workouts";
    private static final String RESULTS_RESOURCE = "results";

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

    @Test @Ignore
    public void shouldSaveResultsForWorkout() {
        final List<Integer> exercises = putExercises("Sledge slams", "Target sprawls", "Assault bike", "Row", "Walking lunges", "Shuttle runs");
        final String workoutId = put(formatJson("170502.json", exercises));

        final String json = null;
        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(json)
                .when()
                .put("{basePath}/{workoutId}/{path}", userWorkoutPath, workoutId, RESULTS_RESOURCE)
                .then()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");
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
}
