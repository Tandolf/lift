package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.andolf.api.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2017-06-24.
 */
@Document(collection = "Resistance")
public class ResistanceEntity {

    @Id
    private Long id;
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

    private List<ExerciseEntity> exercises;

    public ResistanceEntity() {
        this.exercises = new ArrayList<>();
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

    public void addExercise(ExerciseEntity exerciseEntity) {
        exercises.add(exerciseEntity);
    }

    public void isForCal(boolean forCal) {
        this.forCal = forCal;
    }

    public void isForDistance(boolean forDistance) {
        this.forDistance = forDistance;
    }

    public void isStrapless(boolean strapless) {
        this.strapless = strapless;
    }

    public boolean isStrapless() {
        return strapless;
    }

    public List<ExerciseEntity> getExercises() {
        return exercises;
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
}
