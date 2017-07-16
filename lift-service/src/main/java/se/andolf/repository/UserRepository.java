package se.andolf.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.UserEntity;

/**
 * @author Thomas on 2017-07-16.
 */
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {
}
