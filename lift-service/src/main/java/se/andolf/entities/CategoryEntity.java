package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by Thomas on 2016-06-11.
 */
@NodeEntity
public class CategoryEntity {

    @GraphId
    private Long id;

    @Index(unique=true)
    private String name;

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    @Relationship(type = "HAS", direction = Relationship.UNDIRECTED)
    private List<Exercise> exercises;
}
