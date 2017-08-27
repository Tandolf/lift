package se.andolf.api;

/**
 * @author Thomas on 2017-08-27.
 */
public class Result {

    private final int round;
    private final int weight;
    private final int distance;
    private final int calories;
    private final int reps;
    private final int grade;

    public Result(int round, int weight, int distance, int calories, int reps, int grade) {
        this.round = round;
        this.weight = weight;
        this.distance = distance;
        this.calories = calories;
        this.reps = reps;
        this.grade = grade;
    }

    public int getRound() {
        return round;
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

    public int getReps() {
        return reps;
    }

    public int getGrade() {
        return grade;
    }
}
