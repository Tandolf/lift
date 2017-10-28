package se.andolf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.UserEntity;

import java.util.List;

/**
 * @author Thomas on 2017-07-16.
 */
public interface UserRepository extends MongoRepository<UserEntity, String> {


}
