package se.andolf.entities;

import org.neo4j.ogm.annotation.*;

/**
 * @author Thomas on 2017-06-24.
 */
@RelationshipEntity(type="FOR_EXERCISE")
public class ResistanceToExerciseRel {

    @GraphId
    private Long id;

    @Property
    private int order;

    @StartNode
    private ResistanceEntity resistanceEntity;

    @EndNode
    private ExerciseEntity exerciseEntity;

    public ResistanceToExerciseRel() {
    }

    public ResistanceToExerciseRel(int order, ResistanceEntity resistanceEntity, ExerciseEntity exerciseEntity) {
        this.order = order;
        this.resistanceEntity = resistanceEntity;
        this.exerciseEntity = exerciseEntity;
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

    public ResistanceEntity getResistanceEntity() {
        return resistanceEntity;
    }

    public void setResistanceEntity(ResistanceEntity resistanceEntity) {
        this.resistanceEntity = resistanceEntity;
    }

    public ExerciseEntity getExerciseEntity() {
        return exerciseEntity;
    }

    public void setExerciseEntity(ExerciseEntity exerciseEntity) {
        this.exerciseEntity = exerciseEntity;
    }
}
