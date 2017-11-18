package se.andolf.api;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public class Wod {

    private final String id;
    private final ZonedDateTime date;
    private final List<Workout> workouts;

    public Wod(String id, ZonedDateTime date, List<Workout> workouts) {
        this.id = id;
        this.date = date;
        this.workouts = workouts;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public static class Builder {

        private String id;
        private ZonedDateTime date;
        private List<Workout> workouts;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder date(ZonedDateTime date) {
            this.date = date;
            return this;
        }

        public Builder workouts(List<Workout> workouts) {
            this.workouts = workouts;
            return this;
        }

        public Wod build(){
            return new Wod(id, date, workouts);
        }
    }
}
