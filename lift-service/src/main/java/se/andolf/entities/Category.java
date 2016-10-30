package se.andolf.entities;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by Thomas on 2016-06-11.
 */
@NodeEntity
public class Category extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Relationship(type = "HAS", direction = Relationship.UNDIRECTED)
    private List<Exercise> exercises;
}
