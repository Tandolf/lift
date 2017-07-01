package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2017-06-24.
 */
public class Group {

    private final List<Resistance> resistance;
    private final List<Exercise> exercises;

    public Group(List<Resistance> resistance, List<Exercise> exercises) {
        this.resistance = resistance;
        this.exercises = exercises;
    }

    public List<Resistance> getResistance() {
        return resistance;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
