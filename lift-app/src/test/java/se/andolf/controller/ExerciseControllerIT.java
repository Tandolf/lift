package se.andolf.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.api.Exercise;
import se.andolf.util.DbUtil;
import se.andolf.util.UriUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static se.andolf.util.DbUtil.purge;

/**
 *  @author Thomas on 2016-09-03.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class ExerciseControllerIT {

    private static final String RESOURCE = "exercises";
    private boolean initialized = false;

    @Before
    public void init(){
        if(!initialized){
            purge(RESOURCE);
            initialized = true;
        }
    }

    @Test
    public void shouldReturnAFullListOfExercises(){

        final String boxjumps = put(new Exercise("Boxjumps"));
        final String snatch = put(new Exercise("Snatch"));
        final String cleans = put(new Exercise("Cleans"));

        given().log().everything()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(RESOURCE)
                .then()
                .statusCode(200)
                .body("name", hasItems("Boxjumps", "Snatch", "Cleans"));

        delete(RESOURCE, boxjumps, snatch, cleans);
    }

    @Test
    public void shouldReturn200AndAnExercise(){
        final String boxjumps = put(new Exercise("Boxjumps"));

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("{path}/{id}", RESOURCE, boxjumps)
                .then()
                .assertThat().log().everything()
                .statusCode(200)
                .body("name", is("Boxjumps"));

        DbUtil.delete(RESOURCE, boxjumps);
    }

    @Test
    public void shouldReturn200WhenSavingAnExercise(){

        final Exercise boxjumps = new Exercise("Boxjumps");

        final String location = given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(boxjumps)
                .when()
                .put(RESOURCE)
                .then()
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        delete(RESOURCE, UriUtil.extractLastPath(location));
    }

    @Test
    public void shouldReturn409ConflictIfExerciseNameExists(){

        final String boxjumps = put(new Exercise("Boxjumps"));

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(new Exercise("Boxjumps"))
                .when()
                .put(RESOURCE)
                .then()
                .statusCode(409);

        DbUtil.delete(RESOURCE, boxjumps);
    }

    @Test
    public void shouldReturn204NoContentWhenDeletingAnExercise(){

        final String boxjumps = put(new Exercise("Boxjumps"));

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("{path}/{id}", RESOURCE, boxjumps)
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldReturn404NotFoundIfDeletingNonExistingExercise(){

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("{path}/{id}", RESOURCE, 999999)
                .then()
                .statusCode(404);
    }

    private String put(Exercise exercise) {
        try {
            final String header = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).body(exercise).put(RESOURCE).getHeader("Location");
            return UriUtil.extractLastPath(header);
        } catch (Exception e){
            throw new AssertionError(e);
        }
    }

    private void delete(String resource, String... ids) {
        for (String id : ids){
            DbUtil.delete(resource, id);
        }
    }

}
