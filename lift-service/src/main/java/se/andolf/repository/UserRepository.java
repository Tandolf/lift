package se.andolf.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.UserEntity;

import java.util.List;

/**
 * @author Thomas on 2017-07-16.
 */
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {

    @Query("MATCH (u:UserEntity) " +
            "RETURN u")
    List<UserEntity> findAllUsersAsList();
}
