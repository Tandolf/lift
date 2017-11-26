package se.andolf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.WorkoutEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomas on 2017-06-18.
 */
public interface WorkoutRepository extends MongoRepository<WorkoutEntity, String> {

    Optional<WorkoutEntity> findById(String id);
    List<WorkoutEntity> findByUser(String user);
    List<WorkoutEntity> findByDateAndUser(String user, LocalDate date);
}
