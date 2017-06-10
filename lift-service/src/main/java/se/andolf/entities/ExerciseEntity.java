package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.Equipment;

import java.util.List;
import java.util.Set;

/**
 * @author Thomas on 2016-06-11.
 */
@NodeEntity
public class ExerciseEntity {

    @GraphId
    private Long id;

    @Index(unique = true)
    private String name;

    @Relationship(type = "USES", direction = Relationship.UNDIRECTED)
    private List<EquipmentEntity> equipments;

    public ExerciseEntity() {}

    public ExerciseEntity(String name) {
        this.name = name;
    }

    public ExerciseEntity(String name, List<EquipmentEntity> equipmentEntities) {
        this.name = name;
        this.equipments = equipmentEntities;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EquipmentEntity> getEquipments() {
        return equipments;
    }

    public void setEquipment(List<EquipmentEntity> equipments) {
        this.equipments = equipments;
    }
}
