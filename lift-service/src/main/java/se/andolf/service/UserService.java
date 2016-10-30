package se.andolf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.andolf.dto.UserDTO;
import se.andolf.entities.User;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.UserRepository;

import java.io.IOException;
import java.util.List;

/**
 * Created by Thomas on 2016-06-26.
 */
@Service
@Transactional
public class UserService {

    private static Log LOG = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public UserDTO save(UserDTO user) {
        User newUser = modelMapper.map(user, User.class);
        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser, UserDTO.class);
    }

    public UserDTO load(String id) {
        return null;
    }

    public <T> List<T> getAll() {
        return null;
    }

    public void delete(String id) {
        try {
            User user = userRepository.findByUniqueId(id, -1);
            userRepository.delete(user);
        } catch (DataRetrievalFailureException e){
            LOG.warn(e);
            throw new NodeNotFoundException(e);
        }
    }

    public UserDTO getUserById(String id) {
        User user = userRepository.findByUniqueId(id, 1);
        return modelMapper.map(user, UserDTO.class);
    }

    public void patch(JsonPatch jsonPatch, String id) {

        try {
            User user = userRepository.findByUniqueId(id, 1);
            final JsonNode userJson = objectMapper.valueToTree(user);
            final JsonNode patched = jsonPatch.apply(userJson);
            user = objectMapper.readValue(patched.toString(), User.class);
            userRepository.save(user);
        } catch (DataRetrievalFailureException e){
            LOG.error(e);
            throw new NodeNotFoundException("Could not find User: " + id);
        }  catch (IOException | JsonPatchException ex) {
            LOG.debug(ex);
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
