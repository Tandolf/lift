package se.andolf.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.User;

/**
 * Created by Thomas on 2016-06-26.
 */
public interface UserRepository extends Neo4jRepository<User, Long> {

}
