package se.andolf.util;

import org.modelmapper.ModelMapper;
import se.andolf.api.GenderType;
import se.andolf.api.Units;
import se.andolf.configuration.mappers.UserDtoToUserRestMapping;
import se.andolf.dto.UserDTO;
import se.andolf.entities.Body;
import se.andolf.mappers.UserToDtoMapping;
import se.andolf.model.User;

/**
 * Created by Thomas on 2016-08-02.
 */
public class TestData {

    public enum USER {
        CURRENT("John", "Doe", GenderType.MALE, "john@doe.co.uk", 175, 75, Units.METRIC),
        NEW("Thomas", "Andolf", GenderType.MALE, "thomas.andolf@gmail.com", 190, 85, Units.METRIC);

        private se.andolf.entities.User user;
        private User restUser;
        private UserDTO userDTO;

        USER(String firstname, String lastname, GenderType gender, String email, int height, int weight, Units unit) {
            se.andolf.entities.User user = new se.andolf.entities.User();
            Body body = new Body();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setGender(gender);
            body.setHeight(height);
            body.setWeight(weight);
            body.setUnit(unit);
            user.setBody(body);
            this.user = user;

            ModelMapper modelMapper = new ModelMapper();
            UserToDtoMapping userToDtoMapping = new UserToDtoMapping();
            UserDtoToUserRestMapping userDtoToUserRestMapping = new UserDtoToUserRestMapping();

            userToDtoMapping.configure(modelMapper);
            userDtoToUserRestMapping.configure(modelMapper);

            userDTO = modelMapper.map(user, UserDTO.class);
            restUser = modelMapper.map(userDTO, User.class);


        }

        public se.andolf.entities.User getUser() {
            return user;
        }

        public User getRestUser(){
            return restUser;
        }
    }

    public enum EQUIPMENT {
        NEW("Barbell", null), CURRENT("KettleBells", "123"), TOO_LONG_NAME("ThisNameIsWayToLongToSaveAndToPreventFromLongCrappyStringsInTheDb", null);

        private String name;
        private String id;

        EQUIPMENT(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    public enum EXERCISE {
        CURRENT("Squats", "1"), NEW("Deadlift", "2");

        private String name;
        private String id;

        EXERCISE(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }
}