package se.andolf.util;

import org.springframework.http.MediaType;
import se.andolf.api.Exercise;

import static io.restassured.RestAssured.given;

/**
 * @author Thomas on 2017-06-18.
 */
public class ExerciseUtil {

    public static Exercise get(String id){
        try {
            return given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).get("/exercises/{id}", id).as(Exercise.class);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static String put(Exercise exercise) {
        try {
            final String header = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).body(exercise).put("/exercises").then().assertThat().statusCode(201).extract().header("Location");
            return UriUtil.extractLastPath(header);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
