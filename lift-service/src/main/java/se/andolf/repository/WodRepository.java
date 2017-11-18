package se.andolf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.WodEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomas on 2017-06-18.
 */
public interface WodRepository extends MongoRepository<WodEntity, String> {

    List<WodEntity> findByDate(LocalDate date);
    Optional<WodEntity> findById(String id);
    List<WodEntity> findByUsersContaining(String user);

}
