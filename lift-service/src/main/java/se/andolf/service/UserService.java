package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.user.User;
import se.andolf.entities.UserEntity;
import se.andolf.exceptions.DocumentExistsException;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.repository.UserRepository;
import se.andolf.utils.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2017-07-16.
 */
@Service
public class UserService {

    private static final Log LOG = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public String save(User user) {

        final UserEntity userEntity = Mapper.toUserEntity(user);

        try {
            return userRepository.save(userEntity).getId();
        } catch (Exception e) {
            LOG.error(e);
            throw new DocumentExistsException("Email " + user + " exists please select another email");
        }
    }

    public void delete(String id) {
        userRepository.delete(id);
    }

    public List<User> getAll() {
        return userRepository.findAll().stream().map(Mapper::toUser).collect(Collectors.toList());
    }


    public User find(String id) {
        return Optional.ofNullable(userRepository.findOne(id)).map(Mapper::toUser)
                .orElseThrow(() -> new DocomentNotFoundException(String.format("Couldn't not find user with id %s", id)));
    }
}
