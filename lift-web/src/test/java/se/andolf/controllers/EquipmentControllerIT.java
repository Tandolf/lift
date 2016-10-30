package se.andolf.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.util.*;
import se.andolf.model.RESTEquipment;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by Thomas on 2016-07-30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class EquipmentControllerIT {

    private static String PATH_UNDER_TEST = "/equipment";
    private Log log = LogFactory.getLog(EquipmentControllerIT.class);

    @Autowired
    private DatabaseTestUtils databaseTestUtils;

    @Before
    public void init(){
        log.info("------ init EquipmentController integration tests --------");
        log.info(databaseTestUtils);
        databaseTestUtils.clearAll();
        databaseTestUtils.createDb();
    }

    @Test
    public void should_return_200_when_fetching_a_list_of_equipment(){
        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH_UNDER_TEST)
        .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", CoreMatchers.hasItem(TestData.EQUIPMENT.CURRENT.getName()))
                .body("uniqueId", CoreMatchers.hasItem(TestData.EQUIPMENT.CURRENT.getUniqueId()));
    }

    @Test
    public void should_return_404_if_single_equipment_not_found(){
        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH_UNDER_TEST + "/foobar")
        .then()
                .log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void should_return_201_if_new_equipment_was_created(){

        RESTEquipment restEquipment = new RESTEquipment();
        restEquipment.setName(TestData.EQUIPMENT.NEW.getName());

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restEquipment)
        .when()
                .put(PATH_UNDER_TEST)
        .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", is(notNullValue()));
    }

    @Test
    public void should_return_200_if_equipment_was_fetched(){
        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH_UNDER_TEST + "/" + TestData.EQUIPMENT.CURRENT.getUniqueId())
        .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", CoreMatchers.is(equalTo(TestData.EQUIPMENT.CURRENT.getName())))
                .body("uniqueId", CoreMatchers.is(equalTo(TestData.EQUIPMENT.CURRENT.getUniqueId())));
    }

    @Test
    public void should_change_name_of_equipment(){
        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(TestUtils.getPatchString("replace", "name", "kettlebell"))
        .when()
                .patch(PATH_UNDER_TEST + "/" + TestData.EQUIPMENT.CURRENT.getUniqueId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void should_return_400_if_name_too_long(){

        RESTEquipment restEquipment = new RESTEquipment();
        restEquipment.setName(TestData.EQUIPMENT.TOO_LONG_NAME.getName());

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restEquipment)
        .when()
                .put(PATH_UNDER_TEST)
        .then()
                .log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void should_return_409_if_equipment_with_same_name_exists(){

        RESTEquipment restEquipment = new RESTEquipment();
        restEquipment.setName(TestData.EQUIPMENT.CURRENT.getName());

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restEquipment)
        .when()
                .put(PATH_UNDER_TEST)
        .then()
                .log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void should_delete_equipment(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.EQUIPMENT.CURRENT.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void should_return_404_when_trying_to_delete_equipment_that_doesnt_exist(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.EQUIPMENT.NEW.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    //Patch tests
    @Test
    public void should_succesfully_change_an_equipment_name_while_patching(){

        Patch patch = new Patch(PatchOperations.REPLACE, "/name", "Kettlebells");


        List<Patch> a = new ArrayList<>();
        a.add(patch);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when().patch(PATH_UNDER_TEST + "/" + TestData.EQUIPMENT.CURRENT.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    public void should_give_400_if_param_doesnt_exist_while_patching(){

        Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Kettlebells");

        List<Patch> a = new ArrayList<>();
        a.add(patch);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when().patch(PATH_UNDER_TEST + "/" + TestData.EQUIPMENT.CURRENT.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void should_give_404_if_id_doesnt_exist_while_patching(){

        Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Kettlebells");

        List<Patch> a = new ArrayList<>();
        a.add(patch);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when().patch(PATH_UNDER_TEST + "/" + "ABCD")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
