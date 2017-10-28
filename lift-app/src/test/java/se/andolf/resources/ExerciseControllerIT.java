package se.andolf.resources;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.api.Equipment;
import se.andolf.api.Exercise;
import se.andolf.config.Client;
import se.andolf.util.DbUtil;
import se.andolf.util.UriUtil;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static se.andolf.util.DbUtil.purge;

/**
 *  @author Thomas on 2016-09-03.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Ignore
public class ExerciseControllerIT {

    private static final String EXERCISE_RESOURCE = "exercises";
    private static final String EQUIPMENT_RESOURCE = "equipments";
    private boolean initialized = false;

    @BeforeClass
    public static void init(){
        Client.init();
    }

    @Before
    public void purgeDB(){
        if(!initialized){
            purge(EXERCISE_RESOURCE);
            purge(EQUIPMENT_RESOURCE);
            initialized = true;
        }
    }

    @Test
    public void shouldReturnAFullListOfExercises(){

        final String boxjumps = put(new Exercise.Builder().setName("Boxjumps").build());
        final String snatch = put(new Exercise.Builder().setName("Snatch").build());
        final String cleans = put(new Exercise.Builder().setName("Cleans").build());

        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(EXERCISE_RESOURCE)
                .then()
                .statusCode(200)
                .body("name", hasItems("Boxjumps", "Snatch", "Cleans"));

        delete(EXERCISE_RESOURCE, boxjumps, snatch, cleans);
    }

    @Test
    public void shouldReturn200AndAnExercise(){
        final Equipment equipment = new Equipment("Barbell");
        final String equipmentId = DbUtil.put(equipment);
        final Equipment fetchedEquipment = DbUtil.get(equipmentId);
        final List<Equipment> equipments = new ArrayList<>();
        equipments.add(fetchedEquipment);
        final Exercise exercise = new Exercise.Builder().setName("Deadlift").setEquipments(equipments).build();
        final String exerciseId = put(exercise);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("{path}/{id}", EXERCISE_RESOURCE, exerciseId)
                .then()
                .assertThat().log().everything()
                .statusCode(200)
                .body("name", is("Deadlift"))
                .body("equipments", hasSize(1))
                .body("equipments[0].name", is(equipment.getName()));

        DbUtil.delete(EXERCISE_RESOURCE, exerciseId);
        DbUtil.delete(EQUIPMENT_RESOURCE, equipmentId);
    }

    @Test
    public void shouldReturn200WhenSavingAnExercise(){

        final Equipment equipment = new Equipment("Barbell");
        final String equipmentID = DbUtil.put(equipment);
        final Equipment fetchedEquipment = DbUtil.get(equipmentID);
        final List<Equipment> equipments = new ArrayList<>();
        equipments.add(fetchedEquipment);
        final Exercise exercise = new Exercise.Builder().setName("Deadlift").setEquipments(equipments).build();


        final String location = given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(exercise)
                .when()
                .put(EXERCISE_RESOURCE)
                .then()
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        delete(EXERCISE_RESOURCE, UriUtil.extractLastPath(location));
        delete(EQUIPMENT_RESOURCE, equipmentID);
    }

    @Test
    public void shouldReturn404WhenSavingAnExerciseIfAppointedEquipmentDoesnotExists(){

        final Equipment equipment = new Equipment("Barbell");
        final List<Equipment> equipments = new ArrayList<>();
        equipments.add(equipment);
        final Exercise exercise = new Exercise.Builder().setName("Deadlift").setEquipments(equipments).build();

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(exercise)
                .when()
                .put(EXERCISE_RESOURCE)
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldReturn409ConflictIfExerciseNameExists(){

        final Exercise exercise = new Exercise.Builder().setName("Boxjumps").build();
        final String boxjumps = put(exercise);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(exercise)
                .when()
                .put(EXERCISE_RESOURCE)
                .then()
                .statusCode(409);

        DbUtil.delete(EXERCISE_RESOURCE, boxjumps);
    }

    @Test
    public void shouldReturn204NoContentWhenDeletingAnExercise(){

        final String boxjumps = put(new Exercise.Builder().setName("Boxjumps").build());

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("{path}/{id}", EXERCISE_RESOURCE, boxjumps)
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldReturn404NotFoundIfDeletingNonExistingExercise(){

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("{path}/{id}", EXERCISE_RESOURCE, 999999)
                .then()
                .statusCode(404);
    }

    private static String put(Exercise exercise) {
        try {
            final String header = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).body(exercise).put(EXERCISE_RESOURCE).getHeader("Location");
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
