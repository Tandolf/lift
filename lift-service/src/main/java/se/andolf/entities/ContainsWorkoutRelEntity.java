package se.andolf.entities;

import org.neo4j.ogm.annotation.*;

/**
 * @author Thomas on 2017-06-23.
 */
@RelationshipEntity(type="CONTAINS_WORKOUT")
public class ContainsWorkoutRelEntity {

    @GraphId
    private Long relationshipId;

    @Property
    private int group;

    @StartNode
    private WodEntity wodEntity;

    @EndNode
    private WorkoutEntity workoutEntity;

    public ContainsWorkoutRelEntity() {
    }

    public ContainsWorkoutRelEntity(int group, WodEntity wodEntity, WorkoutEntity workoutEntity) {
        this.group = group;
        this.wodEntity = wodEntity;
        this.workoutEntity = workoutEntity;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public WodEntity getWodEntity() {
        return wodEntity;
    }

    public void setWodEntity(WodEntity wodEntity) {
        this.wodEntity = wodEntity;
    }

    public WorkoutEntity getWorkoutEntity() {
        return workoutEntity;
    }

    public void setWorkoutEntity(WorkoutEntity workoutEntity) {
        this.workoutEntity = workoutEntity;
    }
}
