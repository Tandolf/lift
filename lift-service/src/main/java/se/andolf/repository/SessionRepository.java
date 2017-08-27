package se.andolf.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.CategoryEntity;
import se.andolf.entities.SessionEntity;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @author Thomas on 2016-06-11.
 */
public interface SessionRepository extends Neo4jRepository<SessionEntity, Long> {

    @Query("MATCH (w:WorkoutEntity)--(s1:SessionEntity)-[n:FOR_NESTED_SESSION]-(s2:SessionEntity) " +
    "WHERE ID(w)={workoutId} " +
    "RETURN s1, n, s2")
    List<SessionEntity> findAllAsList(Long workoutId);
}
