package se.andolf.util;

import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

/**
 * @author Thomas on 2017-05-25.
 */
public class DbUtil {

    public static void purgeCategories(){
        final String json = given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("categories")
                .then()
                .extract().response()
                .getBody().asString();

        final List<Integer> ids = from(json).get("id");
        ids.forEach(id -> deleteCategory(Integer.toString(id)));
    }

    public static void deleteCategory(String id) {
        try {
            given().delete("/categories/{id}", id).then().assertThat().statusCode(204);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void purgeEquipments(){
        final String json = given()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("equipments")
                .then()
                .extract().response()
                .getBody().asString();

        final List<Integer> ids = from(json).get("id");
        ids.forEach(id -> deleteEquipment(Integer.toString(id)));
    }

    public static void deleteEquipment(String id) {
        try {
            given().delete("/equipments/{id}", id).then().assertThat().statusCode(204);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
