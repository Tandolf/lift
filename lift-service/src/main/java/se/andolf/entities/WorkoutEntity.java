package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.WorkoutType;

import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public class WorkoutEntity {

    @GraphId
    private Long id;
    private int work;
    private int rest;
    private int sets;
    private WorkoutType workoutType;
    private boolean isAlternating;

    @Relationship(type = "BELONGS_TO")
    private List<GroupEntity> groupEntites;

    public WorkoutEntity() {
    }

    public WorkoutEntity(int work, int rest, int sets, WorkoutType workoutType, boolean isAlternating) {
        this.work = work;
        this.rest = rest;
        this.sets = sets;
        this.workoutType = workoutType;
        this.isAlternating = isAlternating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public boolean isAlternating() {
        return isAlternating;
    }

    public void setAlternating(boolean alternating) {
        isAlternating = alternating;
    }

    public List<GroupEntity> getGroupEntites() {
        return groupEntites;
    }

    public void setGroupEntites(List<GroupEntity> groupEntites) {
        this.groupEntites = groupEntites;
    }
}
