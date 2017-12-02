package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import se.andolf.api.workout.ExerciseParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas on 2017-08-27.
 */
@Document(collection = "Result")
@CompoundIndexes({
        @CompoundIndex(name = "user_id_idx", def = "{'user': 1, 'id': 1}", sparse = true)
})
public class ResultEntity {

    @Id
    private final String id;
    private final String user;
    private final String workout;
    private final LocalDateTime date;
    private final List<Map<ExerciseParam, Object>> reps;

    @PersistenceConstructor
    public ResultEntity(String id, String user, String workout, LocalDateTime date, List<Map<ExerciseParam, Object>> reps) {
        this.id = id;
        this.user = user;
        this.workout = workout;
        this.date = date;
        this.reps = reps;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getWorkout() {
        return workout;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<Map<ExerciseParam, Object>> getReps() {
        return reps;
    }

    public static class Builder {

        private String id;
        private String user;
        private String workout;
        private LocalDateTime date;
        private List<Map<ExerciseParam, Object>> reps;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder user(String id) {
            this.user = id;
            return this;
        }

        public Builder workout(String id) {
            this.workout = id;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder reps(List<Map<ExerciseParam, Object>> reps) {
            this.reps = reps;
            return this;
        }

        public ResultEntity build() {
            return new ResultEntity(id, user, workout, date, reps);
        }
    }
}
