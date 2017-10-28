package se.andolf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.CategoryEntity;

import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

}
