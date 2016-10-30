package se.andolf.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.util.Assert;
import se.andolf.entities.Exercise;

/**
 * Created by Thomas on 2016-06-21.
 */
public class ExerciseRepositoryImpl implements CustomExerciseRepository {

    private Log log = LogFactory.getLog(ExerciseRepositoryImpl.class);

    private final Neo4jOperations operations;

    @Autowired
    public ExerciseRepositoryImpl(Neo4jOperations operations){
        Assert.notNull(operations, "Neo4jOperations must not be null");
        this.operations = operations;
    }

    @Override
    public Exercise findByName(String name, int depth) {
        return operations.loadByProperty(Exercise.class, "name", name, 1);
    }

    @Override
    public Exercise findByUniqueId(String id, int depth) {
        return operations.loadByProperty(Exercise.class, "uniqueId", id, 1);
    }
}
