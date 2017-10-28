package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import se.andolf.api.Equipment;
import se.andolf.config.Client;
import se.andolf.config.Environment;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Thomas on 2017-10-14.
 */
public class EquipmentIT {

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations= "/db/equipments.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturnThreeItemsWhenFetchingListOfEquipment(){
        given()
                .accept(ContentType.JSON)
                .when()
                .get("equipments")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(3))
                .body("name", hasItems("Barbell", "Kettlebell", "Box"));
    }

    @Test
    public void shouldReturn404IfEquipmentNotFound(){
        given()
                .accept(ContentType.JSON)
                .when()
                .get("equipments/{id}", "ABC")
                .then()
                .statusCode(404);
    }

    @Test
    @UsingDataSet(locations= "/db/equipments.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn200IfEquipmentWasFetched(){

        given()
                .accept(ContentType.JSON)
                .when()
                .get("equipments/{id}", "59e24b59ca989685da26e4e2")
                .then()
                .statusCode(200)
                .body("name", Matchers.is("Barbell"));
    }

    @Test
    @UsingDataSet(locations= "/db/equipments.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn204WhenDeletingEquipment(){

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("equipments/{id}", "59e24b59ca989685da26e4e2")
                .then()
                .assertThat()
                .statusCode(204);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("equipments")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(2));
    }

    @Test
    public void shouldReturn204WhenTryingToDeleteEquipmentThatDoesNotExist(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("equipments/{id}", "1")
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldReturn204IfNewEquipmentWasCreated(){

        final Equipment equipment = new Equipment("Dumbbell");

        final String header = given()
                .contentType(ContentType.JSON)
                .body(equipment)
                .when()
                .put("equipments")
                .then()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        given()
                .accept(ContentType.JSON)
                .when()
                .get(header)
                .then()
                .statusCode(200)
                .body("name", Matchers.is("Dumbbell"));
    }

    @Test
    @UsingDataSet(locations= "/db/equipments.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn409IfEquipmentWithSameNameExists(){

        given()
                .contentType(ContentType.JSON)
                .body(new Equipment("Barbell"))
                .when()
                .put("equipments")
                .then()
                .statusCode(409);
    }
}
