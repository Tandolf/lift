package se.andolf.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.EquipmentEntity;

/**
 * @author Thomas on 2016-06-18.
 */
public interface EquipmentRepository extends Neo4jRepository<EquipmentEntity, Long> {


}
