package se.andolf.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.ResultEntity;

/**
 * @author Thomas on 2016-06-11.
 */
public interface ResultRepository extends Neo4jRepository<ResultEntity, Long> {


}
