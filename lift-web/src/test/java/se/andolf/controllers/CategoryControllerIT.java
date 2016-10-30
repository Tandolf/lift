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
import se.andolf.util.*;
import se.andolf.model.RESTCategory;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by Thomas on 2016-07-03.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class CategoryControllerIT {

    private Log log = LogFactory.getLog(CategoryControllerIT.class);
    private static String PATH_UNDER_TEST = "/category";

    @Autowired
    private DatabaseTestUtils databaseTestUtils;

    @Before
    public void init(){
        log.info("------ init integration tests --------");
        log.info(databaseTestUtils);
        databaseTestUtils.clearAll();
        databaseTestUtils.createDb(true);
    }

    @Test
    public void should_return_a_correct_list_of_categories(){

        given().log().everything()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(PATH_UNDER_TEST)
                .then().log().all()
                .body("get(0).uniqueId", is(equalTo(TestData.CATEGORY.CURRENT.getUniqueId())))
                .body("get(0).name", is(equalTo(TestData.CATEGORY.CURRENT.getName())));
    }

    @Test
    public void should_return_a_correct_category_by_id(){
        given().log().all()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(PATH_UNDER_TEST + "/" + TestData.CATEGORY.CURRENT.getUniqueId())
                .then().log().all()
                .body("name", is(equalTo(TestData.CATEGORY.CURRENT.getName())))
                .body("uniqueId", is(equalTo(TestData.CATEGORY.CURRENT.getUniqueId())));

    }

    @Test
    public void should_return_a_404_if_category_doesnt_exist(){
        given().log().all()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(PATH_UNDER_TEST + "/" + "abcdef")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void should_save_a_correct_category(){

        RESTCategory restCategory = new RESTCategory();
        restCategory.setName(TestData.CATEGORY.NEW.getName());

        ReflectionUtils.setField(restCategory, "uniqueId", String.class, TestData.CATEGORY.NEW.getUniqueId());

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restCategory)
                .when()
                .put(PATH_UNDER_TEST)
                .then().log().all()
                .header("Location", is(is(notNullValue())));
    }

    @Test
    public void should_return_409_conflict_if_category_name_exists(){

        RESTCategory restCategory = new RESTCategory();
        restCategory.setName(TestData.CATEGORY.CURRENT.getName());

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(restCategory)
                .when()
                .put(PATH_UNDER_TEST)
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());

    }

    @Test
    public void should_delete_an_existing_category(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.CATEGORY.CURRENT.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void should_return_404_not_found_if_deleting_an_non_existing_category(){

        given().log().everything()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete(PATH_UNDER_TEST + "/" + TestData.CATEGORY.NEW.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    //Patch tests
    @Test
    public void should_succesfully_change_a_category_name(){

        Patch patch = new Patch(PatchOperations.REPLACE, "/name", "Arms");
        Patch patch2 = new Patch(PatchOperations.REPLACE, "/name", "Chest");

        List<Patch> a = new ArrayList<>();
        a.add(patch);
        a.add(patch2);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when().patch(PATH_UNDER_TEST + "/" + TestData.CATEGORY.CURRENT.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    public void should_give_400_if_param_doesnt_exist(){

        Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Arms");
        Patch patch2 = new Patch(PatchOperations.REPLACE, "/ne", "Chest");

        List<Patch> a = new ArrayList<>();
        a.add(patch);
        a.add(patch2);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when().patch(PATH_UNDER_TEST + "/" + TestData.CATEGORY.CURRENT.getUniqueId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void should_give_404_if_id_doesnt_exist(){

        Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Arms");

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
