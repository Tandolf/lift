package se.andolf.api;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public class Wod {

    private final Long id;
    private final LocalDate date;
    private final String name;
    private final List<Workout> workouts;

    public Wod(Long id, LocalDate date, String name, List<Workout> workouts) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.workouts = workouts;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public static class Builder {

        private long id;
        private LocalDate date;
        private String name;
        private List<Workout> workouts;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this. date = date;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setWorkouts(List<Workout> workouts) {
            this.workouts = workouts;
            return this;
        }

        public Wod build() {
            return new Wod(id, date, name, workouts);
        }
    }
}
