package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * @author Thomas on 2017-06-24.
 */
public class GroupEntity {

    @GraphId
    private Long id;
    private int value;

    @Relationship(type = "RECOMMENDED_RESISTANCE")
    private List<ResistanceEntity> resistances;

    public GroupEntity(int value, List<ResistanceEntity> resistances) {
        this.value = value;
        this.resistances = resistances;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<ResistanceEntity> getResistances() {
        return resistances;
    }

    public void setResistances(List<ResistanceEntity> resistances) {
        this.resistances = resistances;
    }
}
