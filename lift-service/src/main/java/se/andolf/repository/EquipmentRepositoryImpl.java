package se.andolf.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.util.Assert;
import se.andolf.entities.Equipment;

/**
 * Created by Thomas on 2016-06-26.
 */
public class EquipmentRepositoryImpl implements CustomEquipmentRepository {

    private Log log = LogFactory.getLog(ExerciseRepositoryImpl.class);

    private final Neo4jOperations operations;

    @Autowired
    public EquipmentRepositoryImpl(Neo4jOperations operations){
        Assert.notNull(operations, "Neo4jOperations must not be null");
        this.operations = operations;
    }


    @Override
    public Equipment findByName(String name, int depth) {
        Equipment equipment = null;

        try {
            equipment = operations.loadByProperty(Equipment.class, "name", name, 1);
        } catch (DataRetrievalFailureException e){
            log.error("Could not find node", e);
        }

        return equipment;
    }

    @Override
    public Equipment findByUniqueId(String uniqueId, int depth) {
        return operations.loadByProperty(Equipment.class, "uniqueId", uniqueId, depth);
    }
}
