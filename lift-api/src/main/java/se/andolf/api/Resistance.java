package se.andolf.api;

/**
 * @author Thomas on 2017-06-24.
 */
public class Resistance {

    private final int exerciseId;
    private final int weight;
    private final int distance;
    private final int calories;
    private final int repsFrom;
    private final int repsTo;
    private final boolean altLeftRight;
    private final Unit units;

    public Resistance(int exerciseId, int weight, int distance, int calories, int repsFrom, int repsTo, boolean altLeftRight, Unit units) {
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.distance = distance;
        this.calories = calories;
        this.repsFrom = repsFrom;
        this.repsTo = repsTo;
        this.altLeftRight = altLeftRight;
        this.units = units;
    }

    public int getExerciseId() {
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

    public boolean isAltLeftRight() {
        return altLeftRight;
    }

    public Unit getUnits() {
        return units;
    }
}
