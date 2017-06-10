package se.andolf.config;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import se.andolf.json.JacksonObjectMapper;


/**
 * @author thomas.andolf
 */
public class Client {

    public static void init(){
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(Singleton.INSTANCE.mapperConfig);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private enum Singleton {
        INSTANCE;

        private final Jackson2ObjectMapperFactory jacksonMapper;
        private final ObjectMapperConfig mapperConfig;

        Singleton() {
            jacksonMapper = (aClass, s) -> JacksonObjectMapper.INSTANCE.objectMapper();
            mapperConfig = new ObjectMapperConfig().jackson2ObjectMapperFactory(jacksonMapper);
        }
    }
}
