package se.andolf.repository;


import org.springframework.data.neo4j.repository.GraphRepository;
import se.andolf.entities.Equipment;

/**
 * Created by Thomas on 2016-06-18.
 */
public interface EquipmentRepository extends GraphRepository<Equipment>, CustomEquipmentRepository {

}
