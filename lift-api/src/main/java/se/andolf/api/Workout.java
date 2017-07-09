package se.andolf.api;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public class Workout {

    private final Long id;
    private LocalDate date;
    private final int work;
    private final int rest;
    private final int sets;
    private final int effort;
    private final WorkoutType type;
    private final boolean isAlternating;
    private List<Exercise> exercises;
    private List<Resistance> resistances;
    private List<Group> groups;

    public Workout(Long id, LocalDate date, int work, int rest, int sets, int effort, WorkoutType type, boolean isAlternating, List<Exercise> exercises, List<Resistance> resistances, List<Group> groups) {
        this.id = id;
        this.date = date;
        this.work = work;
        this.rest = rest;
        this.sets = sets;
        this.effort = effort;
        this.type = type;
        this.isAlternating = isAlternating;
        this.exercises = exercises;
        this.resistances = resistances;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
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

    public int getEffort() {
        return effort;
    }

    public WorkoutType getType() {
        return type;
    }

    public boolean isAlternating() {
        return isAlternating;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public List<Resistance> getResistances() {
        return resistances;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public static class Builder {

        private Long id;
        private LocalDate date;
        private int work;
        private int rest;
        private int sets;
        private int effort;
        private WorkoutType workoutType;
        private boolean isAlternating;
        private List<Exercise> exercises;
        private List<Resistance> resistances;
        private List<Group> groups;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder setWork(int work) {
            this.work = work;
            return this;
        }

        public Builder setRest(int rest) {
            this.rest = rest;
            return this;
        }

        public Builder setSets(int sets) {
            this.sets = sets;
            return this;
        }

        public Builder setEffort(int effort) {
            this.effort = effort;
            return this;
        }

        public Builder setWorkoutType(WorkoutType workoutType) {
            this.workoutType = workoutType;
            return this;
        }

        public Builder isAlternating(boolean isAlternating) {
            this.isAlternating = isAlternating;
            return this;
        }

        public Builder setExercises(List<Exercise> exercises) {
            this.exercises = exercises;
            return this;
        }

        public Builder setResistances(List<Resistance> resistances) {
            this.resistances = resistances;
            return this;
        }

        public Builder setGroups(List<Group> groups) {
            this.groups = groups;
            return this;
        }

        public Workout build(){
            return new Workout(id, date, work, rest, sets, effort, workoutType, isAlternating, exercises, resistances, groups);
        }
    }
}
