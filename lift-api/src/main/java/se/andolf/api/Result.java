package se.andolf.api;

import se.andolf.api.workout.ExerciseParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas on 2017-08-27.
 */
public class Result {

    private final String id;
    private final String workout;
    private final List<Map<ExerciseParam, Object>> reps;
    private final LocalDateTime date;

    public Result(String id, String workout, List<Map<ExerciseParam, Object>> reps, LocalDateTime date) {
        this.id = id;
        this.workout = workout;
        this.reps = reps;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getWorkout() {
        return workout;
    }

    public List<Map<ExerciseParam, Object>> getReps() {
        return reps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public static class Builder {

        private String id;
        private String workout;
        private List<Map<ExerciseParam, Object>> reps;
        private LocalDateTime date;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder sets(List<Map<ExerciseParam, Object>> reps) {
            this.reps = reps;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Result build() {
            return new Result(id, workout, reps, date);
        }

        public Builder workout(String id) {
            this.workout = id;
            return this;
        }
    }
}
