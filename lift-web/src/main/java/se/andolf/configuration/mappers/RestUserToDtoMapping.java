package se.andolf.configuration.mappers;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import se.andolf.model.RESTUser;
import se.andolf.dto.UserDTO;

/**
 * Created by Thomas on 2016-09-18.
 */
@Component
public class RestUserToDtoMapping extends PropertyMapConfigurerSupport<RESTUser, UserDTO> {
    @Override
    public PropertyMap<RESTUser, UserDTO> mapping() {
        return new PropertyMap<RESTUser, UserDTO>() {
            @Override
            protected void configure() {
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setGender(source.getGender());
                map().setEmail(source.getEmail());

                map().getBodyDTO().setHeight(source.getRestBody().getHeight());
                map().getBodyDTO().setWeight(source.getRestBody().getWeight());
                map().getBodyDTO().setUnit(source.getRestBody().getUnit());
            }
        };
    }
}
