package se.andolf.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import se.andolf.entities.Category;

/**
 * Created by Thomas on 2016-06-11.
 */
public interface CategoryRepository extends GraphRepository<Category>, CustomCategoryRepository {


}
