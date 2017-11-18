package se.andolf.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import se.andolf.entities.ExerciseEntity;

/**
 * @author Thomas on 2017-11-04.
 */
public class ExerciseRepositoryImpl implements CustomExerciseRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ExerciseRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void deleteAllEquipments(String id) {
        mongoTemplate.updateMulti(new Query(), new Update().pull("equipments", id), ExerciseEntity.class);
    }
}
