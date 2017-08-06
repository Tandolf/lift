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
            "OPTIONAL MATCH (w)--(r:ResistanceEntity) " +
            "OPTIONAL MATCH (w)--(g:GroupEntity) " +
            "DETACH DELETE w, r, g")
    void deleteWorkoutById(long id);

    @Query("MATCH (w:WorkoutEntity) " +
            "RETURN w ORDER BY w.date")
    List<WorkoutEntity> findAllWorkoutsAsList();

    List<WorkoutEntity> findByDate(LocalDate date);

    @Query("MATCH (w:WorkoutEntity)-[rela*..1]-(e1:ExerciseEntity)-[rela2*..1]-(e2:ExerciseEntity) " +
            "WHERE ID(w)={id}" +
            "MATCH (w)-[rela1]-(r:ResistanceEntity)-[rela3*..1]-(e3:ExerciseEntity)" +
            "RETURN w, rela, rela1, rela2, rela3, e1, e2, e3, r")
    WorkoutEntity findById(Long id);
}
