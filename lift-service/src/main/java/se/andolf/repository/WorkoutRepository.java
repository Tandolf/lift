package se.andolf.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.WorkoutEntity;

import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public interface WorkoutRepository extends Neo4jRepository<WorkoutEntity, Long> {

    @Query("MATCH (w:WorkoutEntity) WHERE ID(w)={id} " +
            "OPTIONAL MATCH (w)--(r:ResistanceEntity) " +
            "OPTIONAL MATCH (w)--(g:GroupEntity) " +
            "DETACH DELETE w, r, g")
    void deleteWorkoutById(long id);

    @Query("MATCH (w:WorkoutEntity) RETURN ID(w)")
    List<Long> findAllIdsAsList();
}
