package se.andolf.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import se.andolf.api.Category;
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
import static se.andolf.util.DbUtil.deleteCategory;
import static se.andolf.util.DbUtil.purgeCategories;

/**
 * @author Thomas on 2016-07-03.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class CategoriesControllerIT {

    private static boolean initialized = false;

    @Before
    public void init() {
        if(!initialized){
            purgeCategories();
            initialized = true;
        }
    }

    @Test
    public void shouldReturnListOfCategories(){

        final String arms = put(new Category("Arms"));
        final String legs = put(new Category("Legs"));
        final String chest = put(new Category("Chest"));

        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("/categories")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", hasItems("Arms", "Legs", "Chest"));

        deleteCategory(arms);
        deleteCategory(legs);
        deleteCategory(chest);
    }

    @Test
    public void shouldReturn404IfCategoryDoesntExist(){
        given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("categories/{id}", 9999999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void shouldSaveCategory(){

        final Category category = new Category("Arms");

        final String header = given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
                .when()
                .put("/categories")
                .then()
                .assertThat()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        deleteCategory(UriUtil.extractLastPath(header));
    }

    @Test
    public void shouldReturn409ConflictIfCategoryNameExists(){

        final Category category = new Category("Arms");
        final String id = put(category);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
                .when()
                .put("/categories")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());

        deleteCategory(id);
    }

    @Test
    public void shouldDeleteAnExistingCategory(){

        final String id = put(new Category("Chest"));

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("/categories/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturn404NotFoundIfDeletingAnNonExistingCategory(){

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .delete("/categories/{id}", 999999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    //Patch tests
    @Test @Ignore
    public void shouldSuccessfullyChangeCategoryNameTwice(){

        final String id = put(new Category("Legs"));

        final Patch patch = new Patch(PatchOperations.REPLACE, "/name", "Arms");
        final Patch patch2 = new Patch(PatchOperations.REPLACE, "/name", "Chest");

        final List<Patch> patches = new ArrayList<>();
        patches.add(patch);
        patches.add(patch2);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(patches)
                .when()
                .patch("/categories/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        deleteCategory(id);
    }

    @Test
    public void shouldGive400IfParamDoesntExist(){

        final String id = put(new Category("Legs"));

        final Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Arms");
        final Patch patch2 = new Patch(PatchOperations.REPLACE, "/ne", "Chest");

        final List<Patch> patches = new ArrayList<>();
        patches.add(patch);
        patches.add(patch2);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(patches)
                .when()
                .patch("/categories/{id}", id)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteCategory(id);
    }

    @Test @Ignore
    public void shouldGive404IfIdDoesntExist(){

        final Patch patch = new Patch(PatchOperations.REPLACE, "/na", "Arms");

        final List<Patch> patches = new ArrayList<>();
        patches.add(patch);

        given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(patches)
                .when()
                .patch("/categories/{id}", 99999999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private String put(Category category) {
        try {
            final String header = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).body(category).put("/categories").getHeader("Location");
            return UriUtil.extractLastPath(header);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
