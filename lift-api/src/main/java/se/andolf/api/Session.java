package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2017-06-24.
 */
public class Session {

    private final String id;
    private final String exerciseId;
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
    private final List<Session> sessions;


    public Session(String id, String exerciseId, int weight, int distance, int calories, int repsFrom, int repsTo, boolean alternateSides, Unit units, int effort, boolean forCal, boolean forDistance, boolean strapless, int damper, WorkoutType workoutType, List<Session> sessions) {
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
        this.sessions = sessions;
    }

    public String getId() {
        return id;
    }

    public String getExerciseId() {
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

    public List<Session> getSessions() {
        return sessions;
    }

    public static class Builder {

        private String id;
        private String exerciseId;
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
        private List<Session> sessions;

        public Builder() {
            this.units = Unit.METRIC;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder setExerciseId(String exerciseId) {
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

        public Builder setSessions(List<Session> sessions) {
            this.sessions = sessions;
            return this;
        }

        public Session build() {
            return new Session(id, exerciseId, weight, distance, calories, repsFrom, repsTo, alternateSides, units, effort, forCal, forDistance, strapless, damper, workoutType, sessions);
        }
    }
}
