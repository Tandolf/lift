package se.andolf.repository;


import org.springframework.data.neo4j.repository.GraphRepository;
import se.andolf.entities.Exercise;

/**
 * Created by Thomas on 2016-06-19.
 */
public interface ExerciseRepository extends GraphRepository<Exercise>, CustomExerciseRepository {

}
