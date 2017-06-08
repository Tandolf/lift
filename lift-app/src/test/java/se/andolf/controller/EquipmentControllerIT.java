package se.andolf.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.api.Equipment;
import se.andolf.util.Patch;
import se.andolf.util.PatchOperations;
import se.andolf.util.UriUtil;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static se.andolf.util.DbUtil.deleteEquipment;
import static se.andolf.util.DbUtil.purgeCategories;
import static se.andolf.util.DbUtil.purgeEquipments;

/**
 * @author Thomas on 2016-07-30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class EquipmentControllerIT {

    private static boolean initialized = false;

    @Before
    public void init(){
        if(!initialized){
            purgeEquipments();
            initialized = true;
        }
    }

    @Test
    public void shouldReturn200WhenFetchingListOfEquipment(){
        final String barbell = put(new Equipment("Barbell"));
        final String kettlebell = put(new Equipment("Kettlebell"));
        final String box = put(new Equipment("Box"));

        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get("equipments")
        .then()
                .statusCode(200)
                .body("name", hasItems("Barbell", "Kettlebell", "Box"));

        deleteEquipment(barbell);
        deleteEquipment(kettlebell);
        deleteEquipment(box);
    }

    @Test
    public void shouldReturn404IfEquipmentNotFound(){
        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get("equipments/{id}", 999999)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturn204IfNewEquipmentWasCreated(){

        final Equipment equipment = new Equipment("Barbell");

        final String header = given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(equipment)
                .when()
                .put("equipments")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        deleteEquipment(UriUtil.extractLastPath(header));
    }

    @Test
    public void shouldReturn200IfEquipmentWasFetched(){

        final String id = put(new Equipment("Barbell"));

        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get("equipments/{id}", id)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.is("Barbell"));

        deleteEquipment(id);
    }

    @Test
    public void shouldReturn409IfEquipmentWithSameNameExists(){

        final Equipment barbell = new Equipment("Barbell");
        final String id = put(barbell);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(barbell)
        .when()
                .put("equipments")
        .then()
                .statusCode(HttpStatus.CONFLICT.value());

        deleteEquipment(id);
    }

    @Test
    public void shouldReturn204NoContentWhenDeletingEquipment(){

        final String id = put(new Equipment("Barbell"));

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("equipments/{id}", id)
        .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturn404WhenTryingToDeleteEquipmentThatDoesntExist(){
        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("equipments/{id}", 99999)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    //Patch tests
    @Test @Ignore
    public void shouldSuccesfullyChangeAnEquipmentNameWhilePatching(){

        final String id = put(new Equipment("Barbell"));

        final Patch patch = new Patch(PatchOperations.REPLACE, "/name", "Kettlebells");

        final List<Patch> a = new ArrayList<>();
        a.add(patch);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when()
                .patch("equipments/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get("equipments/{id}", id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.is("Kettlebells"));

        deleteEquipment(id);

    }

    @Test @Ignore
    public void shouldReturn400IfParamDoesntExistWhilePatching(){

        final String id = put(new Equipment("Barbell"));

        final Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Kettlebells");

        final List<Patch> a = new ArrayList<>();
        a.add(patch);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when().patch("equipments/{id}", id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteEquipment(id);

    }

    @Test @Ignore
    public void shouldReturn404IfIdDoesntExistWhilePatching(){

        final Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Kettlebells");

        final List<Patch> a = new ArrayList<>();
        a.add(patch);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(a)
                .when()
                .patch("equipments/{id}", 999999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private String put(Equipment equipment) {
        try {
            final String header = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).body(equipment).put("/equipments").then().assertThat().statusCode(201).extract().header("Location");
            return UriUtil.extractLastPath(header);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
