package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import se.andolf.api.WorkoutType;
import se.andolf.converter.LocalDateConverter;

import java.time.LocalDate;
import java.util.*;

/**
 * @author Thomas on 2017-06-18.
 */
public class WorkoutEntity {

    @GraphId
    private Long id;

    @Convert(LocalDateConverter.class)
    private LocalDate date;

    private int work;
    private int rest;
    private int sets;
    private int effort;
    private WorkoutType workoutType;
    private boolean isAlternating;

    @Relationship(type = "RECOMMENDED_RESISTANCE")
    private List<ResistanceEntity> resistanceEntities;

    @Relationship(type = "INCLUDES")
    private List<SessionEntity> sessionEntities;

    @Relationship(type = "HAS_WORKOUTS", direction = "INCOMING")
    private Set<UserEntity> userEntities;

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
