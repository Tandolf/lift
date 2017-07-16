package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.User;
import se.andolf.api.Workout;
import se.andolf.entities.UserEntity;
import se.andolf.entities.WorkoutEntity;
import se.andolf.repository.UserRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
