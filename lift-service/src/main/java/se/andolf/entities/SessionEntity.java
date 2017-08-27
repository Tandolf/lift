package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.Unit;
import se.andolf.api.WorkoutType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
@NodeEntity
public class SessionEntity {

    @GraphId
    private Long id;
    private int order;
    private WorkoutType workoutType;
    private int weight;
    private int distance;
    private int calories;
    private int repsFrom;
    private int repsTo;
    private boolean alternateSides;
    private boolean forCal;
    private boolean forDistance;
    private Unit units;
    private boolean strapless;
    private int effort;
    private int damper;

    @Relationship(type = "FOR_NESTED_SESSION")
    private List<SessionEntity> sessionEntities;

    @Relationship(type = "FOR_EXERCISE")
    private List<ExerciseEntity> exerciseEntities;

    @Relationship(type = "RECORDED")
    private List<ResultEntity> resultEntities;

    public SessionEntity() {
        sessionEntities = new ArrayList<>();
        exerciseEntities = new ArrayList<>();
        resultEntities = new ArrayList<>();
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

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public List<SessionEntity> getSessionEntities() {
        return sessionEntities;
    }

    public void setSessionEntities(List<SessionEntity> sessionEntities) {
        this.sessionEntities = sessionEntities;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getRepsFrom() {
        return repsFrom;
    }

    public void setRepsFrom(int repsFrom) {
        this.repsFrom = repsFrom;
    }

    public int getRepsTo() {
        return repsTo;
    }

    public void setRepsTo(int repsTo) {
        this.repsTo = repsTo;
    }

    public boolean isAlternateSides() {
        return alternateSides;
    }

    public void setAlternateSides(boolean alternateSides) {
        this.alternateSides = alternateSides;
    }

    public boolean isForCal() {
        return forCal;
    }

    public void setForCal(boolean forCal) {
        this.forCal = forCal;
    }

    public boolean isForDistance() {
        return forDistance;
    }

    public void setForDistance(boolean forDistance) {
        this.forDistance = forDistance;
    }

    public Unit getUnits() {
        return units;
    }

    public void setUnits(Unit units) {
        this.units = units;
    }

    public boolean isStrapless() {
        return strapless;
    }

    public void setStrapless(boolean strapless) {
        this.strapless = strapless;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public int getDamper() {
        return damper;
    }

    public void setDamper(int damper) {
        this.damper = damper;
    }

    public List<ResultEntity> getResultEntities() {
        return resultEntities;
    }

    public void setResultEntities(List<ResultEntity> resultEntities) {
        this.resultEntities = resultEntities;
    }

    public void addResultEntity(ResultEntity resultEntity) {
        resultEntities.add(resultEntity);
    }
}
