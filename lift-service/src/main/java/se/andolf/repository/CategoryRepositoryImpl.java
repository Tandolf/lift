package se.andolf.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.util.Assert;
import se.andolf.entities.Category;

/**
 * Created by Thomas on 2016-06-21.
 */
public class CategoryRepositoryImpl implements CustomCategoryRepository {

    private Log log = LogFactory.getLog(CategoryRepositoryImpl.class);

    private final Neo4jOperations operations;

    @Autowired
    public CategoryRepositoryImpl(Neo4jOperations operations) {
        Assert.notNull(operations, "Neo4jOperations must not be null");
        this.operations = operations;
    }

    @Override
    public Category findByName(String name, int depth) {
        Category category = null;

        try {
            category = operations.loadByProperty(Category.class, "name", name, 1);
        } catch (DataRetrievalFailureException e){
            log.error("Could not find node", e);
        }

        return category;
    }

    @Override
    public Category findByUniqueId(String id, int i) {
        Category category = null;

        try {
            category = operations.loadByProperty(Category.class, "uniqueId", id, 1);
        } catch (DataRetrievalFailureException e){
            log.error("Could not find node", e);
        }

        return category;
    }
}