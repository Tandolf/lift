package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public class Workout {

    private final int work;
    private final int rest;
    private final int sets;
    private final WorkoutType workoutType;
    private final boolean isAlternating;
    private final List<Group> groups;

    public Workout(int work, int rest, int sets, WorkoutType type, boolean isAlternating, List<Group> groups) {
        this.work = work;
        this.rest = rest;
        this.sets = sets;
        this.workoutType = type;
        this.isAlternating = isAlternating;
        this.groups = groups;
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

    public boolean isAlternating() {
        return isAlternating;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
