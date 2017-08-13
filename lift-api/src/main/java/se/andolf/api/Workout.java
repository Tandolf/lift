package se.andolf.api;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public class Workout {

    private final Long id;
    private final LocalDate date;
    private final int work;
    private final int rest;
    private final int sets;
    private final int effort;
    private final WorkoutType workoutType;
    private final boolean isAlternating;
    private final List<Session> sessions;

    public Workout(Long id, LocalDate date, int work, int rest, int sets, int effort, WorkoutType workoutType, boolean isAlternating, List<Session> sessions) {
        this.id = id;
        this.date = date;
        this.work = work;
        this.rest = rest;
        this.sets = sets;
        this.effort = effort;
        this.workoutType = workoutType;
        this.isAlternating = isAlternating;
        this.sessions = sessions;
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

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public boolean isAlternating() {
        return isAlternating;
    }

    public List<Session> getSessions() {
        return sessions;
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
        private List<Session> sessions;

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

        public Builder setSessions(List<Session> sessions) {
            this.sessions = sessions;
            return this;
        }

        public Workout build(){
            return new Workout(id, date, work, rest, sets, effort, workoutType, isAlternating, sessions);
        }
    }
}
