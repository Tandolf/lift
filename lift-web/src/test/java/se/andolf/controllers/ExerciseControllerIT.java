package se.andolf.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.model.RESTExercise;
import se.andolf.util.DatabaseTestUtils;
import se.andolf.util.ReflectionUtils;
import se.andolf.util.TestData;

import static com.jayway.restassured.RestAssured.given;
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

    @Autowired
    private DatabaseTestUtils databaseTestUtils;

    @Before
    public void init(){
        log.info("------ init ExerciseController integration tests --------");
        databaseTestUtils.clearAll();
        databaseTestUtils.createDb(true);
    }

    @Test
    public void should_return_a_full_list_of_exercises(){

        given().log().everything()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(PATH_UNDER_TEST)
                .then().log().all()
                .body("get(0).uniqueId", is(equalTo(TestData.EXERCISE.CURRENT.getUniqueId())))
                .body("get(0).name", is(equalTo(TestData.EXERCISE.CURRENT.getName())));
    }

    @Test
    public void should_return_an_exercise_by_id(){
        given().log().all()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(PATH_UNDER_TEST + "/" + TestData.EXERCISE.CURRENT.getUniqueId())
                .then().log().all()
                .body("name", is(equalTo(TestData.EXERCISE.CURRENT.getName())))
                .body("uniqueId", is(equalTo(TestData.EXERCISE.CURRENT.getUniqueId())));

    }

    @Test
    public void should_save_an_exercise(){

        RESTExercise restExercise = new RESTExercise();
        restExercise.setName(TestData.EXERCISE.NEW.getName());

        ReflectionUtils.setField(restExercise, "uniqueId", String.class, TestData.EXERCISE.NEW.getUniqueId());

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restExercise)
                .when()
                .put(PATH_UNDER_TEST)
                .then().log().all()
                .header("Location", is(notNullValue()));
    }

    @Test
    public void should_return_409_conflict_if_exercise_name_exists(){

        RESTExercise restExercise = new RESTExercise();
        restExercise.setName(TestData.EXERCISE.CURRENT.getName());

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restExercise)
                .when()
                .put(PATH_UNDER_TEST)
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void should_delete_an_exercise(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.EXERCISE.CURRENT.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void should_return_404_not_found_if_deleting_an_non_existing_exercise(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.EXERCISE.NEW.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
