package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * @author Thomas on 2017-11-05.
 */
public class SessionEntity {

    @Id
    private final String id;
    private final String exerciseId;
    private final int repsFrom;
    private final int repsTo;
    private final boolean forDistance;
    private final boolean forCal;
    private final int damper;
    private final int weight;
    private final int distance;
    private final int calories;
    private final int effort;
    private final boolean strapless;

    @PersistenceConstructor
    public SessionEntity(String id, String exerciseId, int repsFrom, int repsTo, boolean forDistance, boolean forCal, int damper, int weight, int distance, int calories, int effort, boolean strapless) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.repsTo = repsTo;
        this.repsFrom = repsFrom;
        this.forDistance = forDistance;
        this.forCal = forCal;
        this.damper = damper;
        this.weight = weight;
        this.distance = distance;
        this.calories = calories;
        this.effort = effort;
        this.strapless = strapless;
    }

    public String getId() {
        return id;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public int getRepsTo() {
        return repsTo;
    }

    public int getRepsFrom() {
        return repsFrom;
    }

    public boolean isForDistance() {
        return forDistance;
    }

    public boolean isForCal() {
        return forCal;
    }

    public int getDamper() {
        return damper;
    }

    public int getWeight() {
        return weight;
    }

    public int getDistance() {
        return distance;
    }

    public int getCalories() {
        return calories;
    }

    public int getEffort() {
        return effort;
    }

    public boolean isStrapless() {
        return strapless;
    }
}
