package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author Thomas on 2016-06-11.
 */
@NodeEntity
public class EquipmentEntity {

    @GraphId
    public Long id;

    @Index(unique = true)
    private String name;

    public EquipmentEntity() { }

    public EquipmentEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
