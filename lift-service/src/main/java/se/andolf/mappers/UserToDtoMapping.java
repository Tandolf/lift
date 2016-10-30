package se.andolf.mappers;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import org.modelmapper.PropertyMap;
import se.andolf.dto.UserDTO;
import se.andolf.entities.User;

/**
 * Created by Thomas on 2016-07-17.
 */
public class UserToDtoMapping extends PropertyMapConfigurerSupport<User, UserDTO> {

    @Override
    public PropertyMap<User, UserDTO> mapping() {
        return new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                map().setLastname(source.getLastname());
                map().setFirstname(source.getFirstname());
                map().setGender(source.getGender());
                map().setEmail(source.getEmail());
                map().getBodyDTO().setHeight(source.getBody().getHeight());
                map().getBodyDTO().setWeight(source.getBody().getWeight());
                map().getBodyDTO().setUnit(source.getBody().getUnit());
            }
        };
    }
}
