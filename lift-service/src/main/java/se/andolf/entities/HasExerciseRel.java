package se.andolf.entities;

import org.neo4j.ogm.annotation.*;

/**
 * @author Thomas on 2017-07-08.
 */
@RelationshipEntity(type="HAS_EXERCISE")
public class HasExerciseRel {

    @GraphId
    private Long id;

    @Property
    private Integer order;

    @StartNode
    private GroupEntity groupEntity;

    @EndNode
    private ExerciseEntity exerciseEntity;

    public HasExerciseRel() {
    }

    public HasExerciseRel(Integer order, GroupEntity groupEntity, ExerciseEntity exerciseEntity) {
        this.order = order;
        this.groupEntity = groupEntity;
        this.exerciseEntity = exerciseEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public ExerciseEntity getExerciseEntity() {
        return exerciseEntity;
    }

    public void setExerciseEntity(ExerciseEntity exerciseEntity) {
        this.exerciseEntity = exerciseEntity;
    }
}
