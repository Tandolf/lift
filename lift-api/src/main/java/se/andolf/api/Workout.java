package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2017-06-24.
 */
public class Workout {

    private final String id;
    private final Job job;
    private final boolean alternating;
    private final List<Routine> routines;

    public Workout(String id, Job job, boolean alternating, List<Routine> routines) {
        this.id = id;
        this.job = job;
        this.alternating = alternating;
        this.routines = routines;
    }

    public String getId() {
        return id;
    }

    public Job getJob() {
        return job;
    }

    public boolean isAlternating() {
        return alternating;
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public static class Builder {

        private String id;
        private Job job;
        private boolean alternating;
        private List<Routine> routines;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder job(Job job) {
            this.job = job;
            return this;
        }

        public Builder alternating(boolean alternating) {
            this.alternating = alternating;
            return this;
        }

        public Builder routines(List<Routine> routines) {
            this.routines = routines;
            return this;
        }

        public Workout build() {
            return new Workout(id, job, alternating, routines);
        }
    }
}
