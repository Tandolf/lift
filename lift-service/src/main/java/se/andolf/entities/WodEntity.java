package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import se.andolf.converter.LocalDateConverter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
@NodeEntity
public class WodEntity {

    @GraphId
    private Long id;

    @Convert(LocalDateConverter.class)
    private LocalDate date;
    private String name;

    @Relationship(type = "CONTAINS_WORKOUT")
    private List<ContainsWorkoutRelEntity> containsWorkoutRelEntities;

    public WodEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ContainsWorkoutRelEntity> getContainsWorkoutRelEntities() {
        return containsWorkoutRelEntities;
    }

    public void setContainsWorkoutRelEntities(List<ContainsWorkoutRelEntity> containsWorkoutRelEntities) {
        this.containsWorkoutRelEntities = containsWorkoutRelEntities;
    }
}
