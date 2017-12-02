package se.andolf.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.ExerciseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Created by Thomas on 2016-06-19.
 */
public interface ExerciseRepository extends MongoRepository<ExerciseEntity, String>, CustomExerciseRepository {

    Optional<ExerciseEntity> findById(String id);

    List<ExerciseEntity> findByNameContainingIgnoreCase(String name);
}
