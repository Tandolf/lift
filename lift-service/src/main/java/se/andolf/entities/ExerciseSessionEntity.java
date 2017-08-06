package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.WorkoutType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
@NodeEntity
public class ExerciseSessionEntity {

    @GraphId
    private Long id;

    private int order;
    private WorkoutType type;

    @Relationship(type = "FOR_NESTED_SESSION")
    private List<ExerciseSessionEntity> exerciseSessionEntities;

    @Relationship(type = "FOR_EXERCISE")
    private List<ExerciseEntity> exerciseEntities;

    public ExerciseSessionEntity() {
        exerciseSessionEntities = new ArrayList<>();
        exerciseEntities = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public WorkoutType getType() {
        return type;
    }

    public void setType(WorkoutType type) {
        this.type = type;
    }

    public List<ExerciseSessionEntity> getExerciseSessionEntities() {
        return exerciseSessionEntities;
    }

    public void setExerciseSessionEntities(List<ExerciseSessionEntity> exerciseSessionEntities) {
        this.exerciseSessionEntities = exerciseSessionEntities;
    }

    public List<ExerciseEntity> getExerciseEntities() {
        return exerciseEntities;
    }

    public void setExerciseEntities(List<ExerciseEntity> exerciseEntities) {
        this.exerciseEntities = exerciseEntities;
    }

    public void addExerciseEntity(ExerciseEntity exerciseEntity) {
        this.exerciseEntities.add(exerciseEntity);
    }
}
