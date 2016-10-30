package se.andolf.configuration.mappers;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import se.andolf.dto.UserDTO;
import se.andolf.model.RESTUser;

/**
 * Created by Thomas on 2016-07-17.
 */
@Component
public class UserDtoToUserRestMapping extends PropertyMapConfigurerSupport<UserDTO, RESTUser> {

    @Override
    public PropertyMap<UserDTO, RESTUser> mapping() {

        return new PropertyMap<UserDTO, RESTUser>() {
            @Override
            protected void configure() {
                map().getRestBody().setHeight(source.getBodyDTO().getHeight());
                map().getRestBody().setWeight(source.getBodyDTO().getWeight());
                map().getRestBody().setUnit(source.getBodyDTO().getUnit());
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setGender(source.getGender());
                map().setEmail(source.getEmail());
            }
        };
    }
}
