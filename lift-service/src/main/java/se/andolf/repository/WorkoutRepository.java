package se.andolf.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.WorkoutEntity;

import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public interface WorkoutRepository extends Neo4jRepository<WorkoutEntity, Long> {

    @Query("MATCH (workout) WHERE ID(workout)={id} " +
            "MATCH (workout)-[*..5]->(resistances:ResistanceEntity) " +
            "MATCH (workout)-[*..5]->(groups:GroupEntity) " +
            "DETACH DELETE workout, resistances, groups")
    void deleteWorkoutById(long id);

    @Query("MATCH (w:WorkoutEntity) RETURN ID(w)")
    List<Long> findAllIdsAsList();
}
