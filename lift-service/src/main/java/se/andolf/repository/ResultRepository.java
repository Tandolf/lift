package se.andolf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.ResultEntity;

import java.util.Optional;

/**
 * @author Thomas on 2016-06-11.
 */
public interface ResultRepository extends MongoRepository<ResultEntity, String> {

    Optional<ResultEntity> findByUserAndId(String user, String id);
}
