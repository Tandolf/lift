package se.andolf.api;

/**
 * @author Thomas on 2017-11-05.
 */
public class Session {

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

    public Session(String id, String exerciseId, int repsFrom, int repsTo, boolean forDistance, boolean forCal, int damper, int weight, int distance, int calories, int effort, boolean strapless) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.repsFrom = repsFrom;
        this.repsTo = repsTo;
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

    public int getRepsFrom() {
        return repsFrom;
    }

    public int getRepsTo() {
        return repsTo;
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

    public static class Builder {

        private String id;
        private String exerciseId;
        private int repsFrom;
        private int repsTo;
        private boolean forDistance;
        private boolean forCal;
        private int damper;
        private int weight;
        private int distance;
        private int calories;
        private int effort;
        private boolean strapless;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder exerciseId(String exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public Builder repsFrom(int repsFrom) {
            this.repsFrom = repsFrom;
            return this;
        }

        public Builder repsTo(int repsTo) {
            this.repsTo = repsTo;
            return this;
        }

        public Builder forDistance(boolean forDistance) {
            this.forDistance = forDistance;
            return this;
        }

        public Builder forCal(boolean forCal) {
            this.forCal = forCal;
            return this;
        }

        public Builder damper(int damper) {
            this.damper = damper;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder effort(int effort) {
            this.effort = effort;
            return this;
        }

        public Builder strapless(boolean strapless) {
            this.strapless = strapless;
            return this;
        }

        public Session build() {
            return new Session(id, exerciseId, repsFrom, repsTo, forDistance, forCal, damper, weight, distance, calories, effort, strapless);
        }
    }
}
