package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.Equipment;

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

    @Relationship(type = "USES")
    private Equipment equipment;

    public ExerciseEntity() {}

    public ExerciseEntity(String name) {
        this.name = name;
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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
