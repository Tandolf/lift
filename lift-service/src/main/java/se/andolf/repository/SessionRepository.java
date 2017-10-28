package se.andolf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.SessionEntity;

import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
public interface SessionRepository extends MongoRepository<SessionEntity, String> {

}
