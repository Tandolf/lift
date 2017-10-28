package se.andolf.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import se.andolf.entities.EquipmentEntity;

/**
 * @author Thomas on 2016-06-18.
 */
public interface EquipmentRepository extends MongoRepository<EquipmentEntity, String> {


}
