package se.andolf.api;

/**
 * @author Thomas on 2017-06-24.
 */
public class Resistance {

    private final Long id;
    private final Long exerciseId;
    private final int weight;
    private final int distance;
    private final int calories;
    private final int repsFrom;
    private final int repsTo;
    private boolean forDistance;
    private boolean forCal;
    private int effort;
    private boolean strapless;
    private final boolean alternateSides;
    private final WorkoutType type;
    private final Unit units;

    public Resistance(Long id, Long exerciseId, int weight, int distance, int calories, int repsFrom, int repsTo, boolean alternateSides, Unit units, int effort, boolean forCal, boolean forDistance, WorkoutType type, boolean strapless) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.distance = distance;
        this.calories = calories;
        this.repsFrom = repsFrom;
        this.repsTo = repsTo;
        this.alternateSides = alternateSides;
        this.units = units;
        this.effort = effort;
        this.forCal = forCal;
        this.forDistance = forDistance;
        this.type = type;
        this.strapless = strapless;
    }

    public Long getId() {
        return id;
    }

    public Long getExerciseId() {
        return exerciseId;
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

    public int getRepsFrom() {
        return repsFrom;
    }

    public int getRepsTo() {
        return repsTo;
    }

    public boolean isAlternateSides() {
        return alternateSides;
    }

    public Unit getUnits() {
        return units;
    }

    public int getEffort() {
        return effort;
    }

    public boolean isForDistance() {
        return forDistance;
    }

    public boolean isForCal() {
        return forCal;
    }

    public WorkoutType getType() {
        return type;
    }

    public boolean isStrapless() {
        return strapless;
    }
}
