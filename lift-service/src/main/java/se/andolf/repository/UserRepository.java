package se.andolf.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import se.andolf.entities.User;

/**
 * Created by Thomas on 2016-06-26.
 */
public interface UserRepository extends GraphRepository<User>, CustomUserRepository<User> {

}
