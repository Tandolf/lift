package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.AccountInfo;
import se.andolf.api.User;
import se.andolf.api.Workout;
import se.andolf.entities.AccountInfoEntity;
import se.andolf.entities.UserEntity;
import se.andolf.entities.WorkoutEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.repository.UserRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2017-07-16.
 */
@Service
public class UserService {

    private final static Log LOG = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public long save(User user) {
        final UserEntity userEntity = toUserEntity(user);

        try {
            return userRepository.save(userEntity).getId();
        } catch (ClientException e) {
            LOG.error(e);
            throw new NodeExistsException("Email " + user.getAccountInfo().getEmail() + " exists please select another email");
        }
    }

    private UserEntity toUserEntity(User user) {
        return new UserEntity.Builder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .addAccountInfo(toAccountInfoEntity(user.getAccountInfo()))
                .build();
    }

    private AccountInfoEntity toAccountInfoEntity(AccountInfo accountInfo) {
        return new AccountInfoEntity.Builder().setEmail(accountInfo.getEmail()).build();
    }

    public void delete(long id) {
        userRepository.deleteUserById(id);
    }

    public List<User> getAll() {
        return userRepository.findAllUsersAsList().stream().map(this::toUser).collect(Collectors.toList());
    }

    private User toUser(UserEntity userEntity) {
        return new User.Builder()
                .setId(userEntity.getId())
                .setFirstname(userEntity.getFirstname())
                .setLastname(userEntity.getLastname())
                .build();
    }
}
