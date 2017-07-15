package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.WorkoutType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2017-06-24.
 */
public class GroupEntity {

    @GraphId
    private Long id;
    private int value;
    private WorkoutType type;

    @Relationship(type = "HAS_EXERCISE")
    private List<HasExerciseRel> hasExerciseRels;

    public GroupEntity() {
        hasExerciseRels = new ArrayList<>();
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

    public WorkoutType getType() {
        return type;
    }

    public void setType(WorkoutType type) {
        this.type = type;
    }

    public void addExercise(HasExerciseRel hasExerciseRels) {
        this.hasExerciseRels.add(hasExerciseRels);
    }

    public List<HasExerciseRel> getHasExerciseRels() {
        return hasExerciseRels;
    }
}
