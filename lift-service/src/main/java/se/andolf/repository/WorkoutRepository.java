package se.andolf.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.WorkoutEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public interface WorkoutRepository extends Neo4jRepository<WorkoutEntity, Long> {

    @Query("MATCH (w:WorkoutEntity) " +
            "WHERE ID(w)={id} " +
            "OPTIONAL MATCH (w)-[*1..2]-(se:SessionEntity) " +
            "OPTIONAL MATCH (w)--(se)--(re:ResultEntity)" +
            "DETACH DELETE w, se, re")
    void deleteWorkoutById(long id);

    @Query("MATCH (w:WorkoutEntity) " +
            "RETURN w ORDER BY w.date")
    List<WorkoutEntity> findAllWorkoutsAsList();

    List<WorkoutEntity> findByDate(LocalDate date);

    @Query("MATCH workout=(w:WorkoutEntity)-[*]->(:ExerciseEntity) " +
            "WHERE ID(w)={id} " +
            "RETURN nodes(workout), relationships(workout)")
    WorkoutEntity findById(Long id);
}
