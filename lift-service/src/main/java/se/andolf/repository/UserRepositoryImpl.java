package se.andolf.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.util.Assert;
import se.andolf.entities.User;
import se.andolf.exceptions.NodeNotFoundException;

/**
 * Created by Thomas on 2016-06-26.
 */
public class UserRepositoryImpl implements CustomUserRepository<User> {

    private Log log = LogFactory.getLog(UserRepositoryImpl.class);

    private final Neo4jOperations operations;

    @Autowired
    public UserRepositoryImpl(Neo4jOperations operations){
        Assert.notNull(operations, "Neo4jOperations must not be null");
        this.operations = operations;
    }


    @Override
    public User findByName(String name, int depth) {
        User user = null;

        try {
            user = operations.loadByProperty(User.class, "name", name, depth);
        } catch (DataRetrievalFailureException e){
            log.error("Could not find node", e);
        }

        return user;
    }

    @Override
    public User findByUniqueId(String uniqueId, int depth) {
            return operations.loadByProperty(User.class, "uniqueId", uniqueId, depth);
    }

    @Override
    public void delete(User entity) {
        try {
            operations.delete(entity.getBody());
            operations.delete(entity);
        } catch (DataRetrievalFailureException e){
            log.error("Could not find node", e);
            throw new NodeNotFoundException("Could not find node with id: " + entity.getUniqueId());
        }
    }


}
