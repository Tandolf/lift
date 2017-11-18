package se.andolf.api;

/**
 * @author Thomas on 2017-11-04.
 */
public class Job {

    private final int work;
    private final int rest;
    private final int sets;
    private final WorkoutType workoutType;

    public Job(int work, int rest, int sets, WorkoutType workoutType) {
        this.work = work;
        this.rest = rest;
        this.sets = sets;
        this.workoutType = workoutType;
    }

    public int getWork() {
        return work;
    }

    public int getRest() {
        return rest;
    }

    public int getSets() {
        return sets;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }
}
