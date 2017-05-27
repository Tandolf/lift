package se.andolf.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.CategoryEntity;

import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
public interface CategoryRepository extends Neo4jRepository<CategoryEntity, Long> {


    List<CategoryEntity> findByName(String name, int i);

    CategoryEntity findById(String uniqueId, int i);
}
