package se.andolf.config;

import io.restassured.RestAssured;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import se.andolf.json.JacksonObjectMapper;

import static io.restassured.RestAssured.oauth2;

/**
 * @author thomas.andolf
 */
public class Client {

    private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1OWUyNGI1OWNhOTg5Njg1ZGEyNmU0ZTEiLCJ1c2VyX25hbWUiOiJqb2huLmRvZUBnbWFpbC5jb20iLCJzY29wZSI6WyJ1c2VyIl0sImV4cCI6MTU0MjU1Mzc3NywiYXV0aG9yaXRpZXMiOlsiQURNSU4iXSwianRpIjoiYWNjMGJlMmEtZTUzZi00NzJiLTlkMzctNTI2ZmExNjhmNDQ0IiwiY2xpZW50X2lkIjoiaW9zIn0.KXd8MTOTiNKH6qJ6UVZdtet9uyhN086V_rkaiA8jrgo_86bgfTbXoia6Huoo5VsjmwmJj-cDkyZVamrM2x3YauQ3OP_pMfDTaDHZ6OtDGwbEvvNwIUuH3IE94DJQDJlOUDLiuZY44d7I7lohWiPMF0tCR5ovrDeRFuhpy2XmYYctJvHgYvjoU3ZE2YheFC3lEm0_oU_EuQMWLnmcH0LubP0O9m3GQwQrxk85rXiaeT7Kr6i0n5sdmTxR1L7PpKx9_0cG-dnFXrK4KEWLfDXnQUTfeLJfxf7lM9n--uXgO5jpKGnlFEkMipSzukkHhC8wjD5dawlg8zc2Ek4f1tCxnQ";

    public static void init(){
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(Singleton.INSTANCE.mapperConfig);
        RestAssured.baseURI = Environment.current().baseURI();
        RestAssured.urlEncodingEnabled = false;
        RestAssured.port = Environment.current().port();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        PreemptiveOAuth2HeaderScheme myScheme = new PreemptiveOAuth2HeaderScheme();
        myScheme.setAccessToken(TOKEN);
        RestAssured.authentication = myScheme;
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
