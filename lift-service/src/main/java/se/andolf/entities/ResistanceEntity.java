package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.Unit;

/**
 * @author Thomas on 2017-06-24.
 */
public class ResistanceEntity {

    @GraphId
    private Long id;
    private int weight;
    private int distance;
    private int calories;
    private int repsFrom;
    private int repsTo;
    private boolean altLeftRight;
    private Unit units;

    @Relationship(type = "FOR_EXERCISE")
    private ResistanceToExerciseRel resistanceToExerciseRel;

    public ResistanceEntity() {
    }

    public ResistanceEntity(int weight, int distance, int calories, int repsFrom, int repsTo, boolean altLeftRight, Unit units) {
        this.weight = weight;
        this.distance = distance;
        this.calories = calories;
        this.repsFrom = repsFrom;
        this.repsTo = repsTo;
        this.altLeftRight = altLeftRight;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isAltLeftRight() {
        return altLeftRight;
    }

    public void setAltLeftRight(boolean altLeftRight) {
        this.altLeftRight = altLeftRight;
    }

    public Unit getUnits() {
        return units;
    }

    public void setUnits(Unit units) {
        this.units = units;
    }

    public ResistanceToExerciseRel getResistanceToExerciseRel() {
        return resistanceToExerciseRel;
    }

    public void setResistanceToExerciseRel(ResistanceToExerciseRel resistanceToExerciseRel) {
        this.resistanceToExerciseRel = resistanceToExerciseRel;
    }
}
