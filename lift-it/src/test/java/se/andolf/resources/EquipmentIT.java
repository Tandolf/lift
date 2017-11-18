package se.andolf.resources;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
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
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Thomas on 2017-10-14.
 */
public class EquipmentIT {

    private final static String EXERCISE_RESOURCE = "exercises";
    private static final String EQUIPMENT_RESOURCE = "equipments";

    @BeforeClass
    public static void init() {
        Client.init();
    }

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("lift").host(Environment.current().host()).build());

    @Test
    @UsingDataSet(locations= "/db/equipments.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturnItemsWhenFetchingListOfEquipment(){
        get(EQUIPMENT_RESOURCE)
                .statusCode(200)
                .body("$.size()", equalTo(7))
                .body("name", hasItems("Barbell", "Kettlebell", "Box"));
    }

    @Test
    public void shouldReturn404IfEquipmentNotFound(){
        get(EQUIPMENT_RESOURCE, "ABC")
                .statusCode(404);
    }

    @Test
    @UsingDataSet(locations= "/db/equipments.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn200IfEquipmentWasFetched(){
        get(EQUIPMENT_RESOURCE, "59e24b59ca989685da26e4e2")
                .statusCode(200)
                .body("name", Matchers.is("Barbell"));
    }

    @Test
    @UsingDataSet(locations= { "/db/equipments.json", "/db/exercises.json"}, loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturn204WhenDeletingEquipment(){
        delete(EQUIPMENT_RESOURCE,"59e24b59ca989685da26e4e3")
                .statusCode(204);
        get(EQUIPMENT_RESOURCE)
                .statusCode(200)
                .body("$.size()", equalTo(6));
        get(EXERCISE_RESOURCE, "59e24b59ca989685da26e4f1")
                .statusCode(200)
                .body("$", not(hasKey("equipments")));
    }

    @Test
    public void shouldReturn204WhenTryingToDeleteEquipmentThatDoesNotExist(){
        delete(EQUIPMENT_RESOURCE, "1")
                .statusCode(204);
    }

    @Test
    public void shouldReturn204IfNewEquipmentWasCreated(){

        final Equipment equipment = new Equipment("SomEquipment");

        final String header = given()
                .contentType(ContentType.JSON)
                .body(equipment)
                .when()
                .put("equipments")
                .then()
                .statusCode(201)
                .header("Location", is(notNullValue()))
                .extract().response().getHeader("Location");

        get(header)
                .statusCode(200)
                .body("name", Matchers.is("SomEquipment"));
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

    private static ValidatableResponse delete(String resource, String id){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete(resource + "/{id}", id)
                .then();
    }

    private static ValidatableResponse get(String resource) {
        return get(resource, "");
    }

    private static ValidatableResponse get(String resource, String id) {
        return given()
                .accept(ContentType.JSON)
                .when()
                .get(resource + "/{id}", id)
                .then();
    }

}
