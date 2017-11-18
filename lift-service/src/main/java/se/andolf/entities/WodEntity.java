package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
@Document(collection = "Wod")
@CompoundIndexes({
        @CompoundIndex(name = "wod_idx", def = "{'date': 1, 'users': 1}")
})
public class WodEntity {

    @Id
    private final String id;
    private final LocalDateTime date;
    private final ZoneId zoneId;
    private final List<WorkoutEntity> workouts;
    private final List<String> users;

    @PersistenceConstructor
    public WodEntity(String id, LocalDateTime date, ZoneId zoneId, List<WorkoutEntity> workouts, List<String> users) {
        this.id = id;
        this.date = date;
        this.zoneId = zoneId;
        this.workouts = workouts;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public List<WorkoutEntity> getWorkouts() {
        return workouts;
    }

    public static class Builder {

        private String id;
        private LocalDateTime date;
        private ZoneId zoneId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder zoneId(ZoneId zoneId) {
            this.zoneId = zoneId;
            return this;
        }

        public WodEntity build() {
            return new WodEntity(id, date, zoneId, null, null);
        }
    }
}
