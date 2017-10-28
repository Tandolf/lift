package se.andolf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.ResultEntity;

/**
 * @author Thomas on 2016-06-11.
 */
public interface ResultRepository extends MongoRepository<ResultEntity, String> {


}
