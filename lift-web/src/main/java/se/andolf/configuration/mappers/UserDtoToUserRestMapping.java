package se.andolf.configuration.mappers;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import se.andolf.dto.UserDTO;
import se.andolf.model.User;

/**
 * Created by Thomas on 2016-07-17.
 */
@Component
public class UserDtoToUserRestMapping extends PropertyMapConfigurerSupport<UserDTO, User> {

    @Override
    public PropertyMap<UserDTO, User> mapping() {

        return new PropertyMap<UserDTO, User>() {
            @Override
            protected void configure() {
                map().getBody().setHeight(source.getBodyDTO().getHeight());
                map().getBody().setWeight(source.getBodyDTO().getWeight());
                map().getBody().setUnit(source.getBodyDTO().getUnit());
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setGender(source.getGender());
                map().setEmail(source.getEmail());
            }
        };
    }
}
