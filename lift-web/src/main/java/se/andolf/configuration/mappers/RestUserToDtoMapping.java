package se.andolf.configuration.mappers;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import se.andolf.model.User;
import se.andolf.dto.UserDTO;

/**
 * Created by Thomas on 2016-09-18.
 */
@Component
public class RestUserToDtoMapping extends PropertyMapConfigurerSupport<User, UserDTO> {
    @Override
    public PropertyMap<User, UserDTO> mapping() {
        return new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setGender(source.getGender());
                map().setEmail(source.getEmail());

                map().getBodyDTO().setHeight(source.getBody().getHeight());
                map().getBodyDTO().setWeight(source.getBody().getWeight());
                map().getBodyDTO().setUnit(source.getBody().getUnit());
            }
        };
    }
}
