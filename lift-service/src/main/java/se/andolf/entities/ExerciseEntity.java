package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
@Document(collection = "Exercise")
public class ExerciseEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private List<EquipmentEntity> equipments;

    @PersistenceConstructor
    public ExerciseEntity(String name, List<EquipmentEntity> equipmentEntities) {
        this.name = name;
        this.equipments = equipmentEntities;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<EquipmentEntity> getEquipments() {
        return equipments;
    }
}
