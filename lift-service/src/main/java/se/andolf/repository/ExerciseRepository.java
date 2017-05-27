package se.andolf.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.Exercise;

/**
 * Created by Thomas on 2016-06-19.
 */
public interface ExerciseRepository extends Neo4jRepository<Exercise, Long> {

    void findByName(String name, int i);
}
