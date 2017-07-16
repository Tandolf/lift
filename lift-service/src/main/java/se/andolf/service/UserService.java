package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.User;
import se.andolf.entities.UserEntity;
import se.andolf.repository.UserRepository;

/**
 * @author Thomas on 2017-07-16.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public long save(User user) {

        final UserEntity userEntity = toUserEntity(user);

        return userRepository.save(userEntity).getId();
    }

    private UserEntity toUserEntity(User user) {
        return new UserEntity.Builder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname()).build();
    }

    public void delete(long id) {
        userRepository.delete(id);
    }
}
