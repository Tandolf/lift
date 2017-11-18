package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import se.andolf.api.Job;

import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
public class WorkoutEntity {

    @Id
    private final String id;
    private final String name;
    private final Job job;
    private final boolean alternating;
    private List<RoutineEntity> routines;

    @PersistenceConstructor
    public WorkoutEntity(String id, String name, Job job, boolean alternating, List<RoutineEntity> routines) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.alternating = alternating;
        this.routines = routines;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }

    public boolean isAlternating() {
        return alternating;
    }

    public List<RoutineEntity> getRoutines() {
        return routines;
    }

    public void setRoutines(List<RoutineEntity> routines) {
        this.routines = routines;
    }
}
