package se.andolf.api.workout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas on 2017-11-26.
 */
public class Workout {

    private final String id;
    private final String user;
    private int effort;
    private Job job;
    private LocalDateTime date;
    private String description;
    private final List<List<Work>> jobs;

    public Workout(String id, String user, int effort, Job job, LocalDateTime date, String description, List<List<Work>> jobs) {
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
        private int effort;
        private Job job;
        private LocalDateTime date;
        private String description;
        private List<List<Work>> jobs = new ArrayList<>();
        private String user;

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

        public Builder addWork(Work... jobs) {
            this.jobs.add(Arrays.asList(jobs));
            return this;
        }

        public Builder jobs(List<List<Work>> jobs) {
            this.jobs = jobs;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Workout build() {
            return new Workout(id, user, effort, job, date, description, jobs);
        }
    }
}
