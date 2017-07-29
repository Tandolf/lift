package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.AccountInfo;
import se.andolf.api.ContactInfo;
import se.andolf.api.User;
import se.andolf.entities.AccountInfoEntity;
import se.andolf.entities.ContactInfoEntity;
import se.andolf.entities.UserEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.UserRepository;
import se.andolf.utils.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2017-07-16.
 */
@Service
public class UserService {

    private static final Log LOG = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public long save(User user) {
        final UserEntity userEntity = Mapper.toUserEntity(user);

        try {
            return userRepository.save(userEntity).getId();
        } catch (ClientException e) {
            LOG.error(e);
            throw new NodeExistsException("Email " + user.getAccountInfo().getEmail() + " exists please select another email");
        }
    }

    public void delete(long id) {
        userRepository.deleteUserById(id);
    }

    public List<User> getAll() {
        return userRepository.findAllUsersAsList().stream().map(Mapper::toUser).collect(Collectors.toList());
    }


    public User find(Long id) {
        final Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findById(id));
        if(userEntity.isPresent())
            return Mapper.toUser(userEntity.get());
        throw new NodeNotFoundException(String.format("Couldn't not find user with id %d", id));
    }
}
