package se.andolf.repository;

import se.andolf.entities.Equipment;

/**
 * Created by Thomas on 2016-06-26.
 */
public interface CustomEquipmentRepository {

    Equipment findByName(String name, int depth);
    Equipment findByUniqueId(String uniqueId, int depth);
}
