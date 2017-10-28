package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import se.andolf.api.WorkoutType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Thomas on 2017-06-18.
 */
@Document(collection = "Workout")
public class WorkoutEntity {

    @Id
    private Long id;

    private LocalDate date;

    private int work;
    private int rest;
    private int sets;
    private int effort;
    private WorkoutType workoutType;
    private boolean isAlternating;

    private List<ResistanceEntity> resistanceEntities;

    private List<SessionEntity> sessionEntities;

    private Set<UserEntity> userEntities;

    @PersistenceConstructor
    public WorkoutEntity() {
        this.resistanceEntities = new ArrayList<>();
        this.sessionEntities = new ArrayList<>();
        this.userEntities = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public int getEffort() {
        return effort;
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

    public void addResistanceRelation(ResistanceEntity resistanceEntity) {
        resistanceEntities.add(resistanceEntity);
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public List<ResistanceEntity> getResistanceEntities() {
        return resistanceEntities;
    }

    public List<SessionEntity> getSessionEntities() {
        return sessionEntities;
    }

    public void addUserEntity(UserEntity userEntity) {
        this.userEntities.add(userEntity);
    }

    public void setSessionEntities(List<SessionEntity> sessionEntity) {
        this.sessionEntities = sessionEntity;
    }
}
