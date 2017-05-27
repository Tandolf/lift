package se.andolf.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.model.Exercise;
import se.andolf.util.TestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by Thomas on 2016-09-03.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class ExerciseControllerIT {

    private Log log = LogFactory.getLog(ExerciseControllerIT.class);
    private static String PATH_UNDER_TEST = "/exercise";

    @Before
    public void init(){
    }

    @Test @Ignore
    public void should_return_a_full_list_of_exercises(){

        given().log().everything()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(PATH_UNDER_TEST)
                .then()
                .body("get(0).uniqueId", is(equalTo(TestData.EXERCISE.CURRENT.getId())))
                .body("get(0).name", is(equalTo(TestData.EXERCISE.CURRENT.getName())));
    }

    @Test @Ignore
    public void should_return_an_exercise_by_id(){
        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(PATH_UNDER_TEST + "/" + TestData.EXERCISE.CURRENT.getId())
                .then()
                .body("name", is(equalTo(TestData.EXERCISE.CURRENT.getName())))
                .body("uniqueId", is(equalTo(TestData.EXERCISE.CURRENT.getId())));

    }

    @Test @Ignore
    public void should_save_an_exercise(){

        Exercise restExercise = new Exercise();
        restExercise.setName(TestData.EXERCISE.NEW.getName());

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restExercise)
                .when()
                .put(PATH_UNDER_TEST)
                .then()
                .header("Location", is(notNullValue()));
    }

    @Test @Ignore
    public void should_return_409_conflict_if_exercise_name_exists(){

        Exercise restExercise = new Exercise();
        restExercise.setName(TestData.EXERCISE.CURRENT.getName());

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restExercise)
                .when()
                .put(PATH_UNDER_TEST)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test @Ignore
    public void should_delete_an_exercise(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.EXERCISE.CURRENT.getId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test @Ignore
    public void should_return_404_not_found_if_deleting_an_non_existing_exercise(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.EXERCISE.NEW.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
