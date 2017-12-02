package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import se.andolf.api.workout.Job;
import se.andolf.api.workout.Work;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
@Document(collection = "Workout")
@CompoundIndexes({
        @CompoundIndex(name = "user_date_idx", def = "{'user': 1, 'date': 1}", sparse = true)
})
public class WorkoutEntity {

    @Id
    private final String id;
    private final String user;
    private final int effort;
    private final Job job;
    private final LocalDateTime date;
    private final String description;
    private final List<List<Work>> jobs;

    @PersistenceConstructor
    public WorkoutEntity(String id, String user, int effort, Job job, LocalDateTime date, String description, List<List<Work>> jobs) {
        this.id = id;
        this.user = user;
        this.effort = effort;
        this.job = job;
        this.date = date;
        this.description = description;
        this.jobs = jobs;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public int getEffort() {
        return effort;
    }

    public Job getJob() {
        return job;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public List<List<Work>> getJobs() {
        return jobs;
    }

    public static final class Builder {
        private String id;
        private String user;
        private int effort;
        private Job job;
        private LocalDateTime date;
        private String description;
        private List<List<Work>> jobs;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder effort(int effort) {
            this.effort = effort;
            return this;
        }

        public Builder job(Job job) {
            this.job = job;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder jobs(List<List<Work>> jobs) {
            this.jobs = jobs;
            return this;
        }

        public WorkoutEntity build() {
            return new WorkoutEntity(id, user, effort, job, date, description, jobs);
        }
    }
}
