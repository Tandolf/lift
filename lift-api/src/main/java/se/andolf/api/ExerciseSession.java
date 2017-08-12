package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2017-06-24.
 */
public class ExerciseSession {

    private final Long id;
    private final Long exerciseId;
    private final int weight;
    private final int distance;
    private final int calories;
    private final int repsFrom;
    private final int repsTo;
    private final boolean forDistance;
    private final boolean forCal;
    private final int effort;
    private final boolean strapless;
    private final int damper;
    private final boolean alternateSides;
    private final Unit units;
    private final WorkoutType workoutType;
    private final List<ExerciseSession> exerciseSessions;


    public ExerciseSession(Long id, Long exerciseId, int weight, int distance, int calories, int repsFrom, int repsTo, boolean alternateSides, Unit units, int effort, boolean forCal, boolean forDistance, boolean strapless, int damper, WorkoutType workoutType, List<ExerciseSession> exerciseSessions) {
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
        this.strapless = strapless;
        this.damper = damper;
        this.workoutType = workoutType;
        this.exerciseSessions = exerciseSessions;
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

    public boolean isStrapless() {
        return strapless;
    }

    public int getDamper() {
        return damper;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public List<ExerciseSession> getExerciseSessions() {
        return exerciseSessions;
    }

    public static class Builder {

        private Long id;
        private Long exerciseId;
        private int weight;
        private int distance;
        private int calories;
        private int repsFrom;
        private int repsTo;
        private boolean forDistance;
        private boolean forCal;
        private int effort;
        private boolean strapless;
        private boolean alternateSides;
        private Unit units;
        private int damper;
        private WorkoutType workoutType;
        private List<ExerciseSession> exerciseSessions;

        public Builder() {
            this.units = Unit.METRIC;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder setExerciseId(Long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public Builder setDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder setRepsFrom(int repsFrom) {
            this.repsFrom = repsFrom;
            return this;
        }

        public Builder setRepsTo(int repsTo) {
            this.repsTo = repsTo;
            return this;
        }

        public Builder setForDistance(boolean forDistance) {
            this.forDistance = forDistance;
            return this;
        }

        public Builder setForCal(boolean forCal) {
            this.forCal = forCal;
            return this;
        }

        public Builder setEffort(int effort) {
            this.effort = effort;
            return this;
        }

        public Builder isStrapless(boolean strapless) {
            this.strapless = strapless;
            return this;
        }

        public Builder isAlternateSides(boolean alternateSides) {
            this.alternateSides = alternateSides;
            return this;
        }

        public Builder setUnits(Unit units) {
            this.units = units;
            return this;
        }

        public Builder setDamper(int damper) {
            this.damper = damper;
            return this;
        }

        public Builder setWorkoutType(WorkoutType workoutType) {
            this.workoutType = workoutType;
            return this;
        }

        public Builder setExerciseSessions(List<ExerciseSession> exerciseSessions) {
            this.exerciseSessions = exerciseSessions;
            return this;
        }

        public ExerciseSession build() {
            return new ExerciseSession(id, exerciseId, weight, distance, calories, repsFrom, repsTo, alternateSides, units, effort, forCal, forDistance, strapless, damper, workoutType, exerciseSessions);
        }
    }
}
