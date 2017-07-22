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

    @Query("MATCH (u:UserEntity)-->(a:AccountInfoEntity) " +
            "WHERE ID(u) = {id} " +
            "DETACH DELETE u, a")
    void deleteUserById(long id);

    @Query("MATCH (u:UserEntity)-[r*0..1]-(x) " +
            "WHERE ID(u) = {id}" +
            "RETURN u, r, x")
    UserEntity findById(Long id);
}
