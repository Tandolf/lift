package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2017-08-27.
 */
@NodeEntity
public class ResultEntity {

    @GraphId
    private Long id;
    private int round;
    private int weight;
    private int distance;
    private int calories;
    private int reps;
    private int grade;

    @Relationship(type = "RECORDED", direction = Relationship.INCOMING)
    private final List<SessionEntity> sessionEntities;

    public ResultEntity() {
        sessionEntities = new ArrayList<>();
    }

    private ResultEntity(int round, int weight, int distance, int calories, int reps, int grade, List<SessionEntity> sessionEntities) {
        this.round = round;
        this.weight = weight;
        this.distance = distance;
        this.calories = calories;
        this.reps = reps;
        this.grade = grade;
        this.sessionEntities = sessionEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRound() {
        return round;
    }

    public int getWeight() {
        return weight;
    }

    public int getDistance() {
        return distance;
    }

    public int getCalories() {
        return calories;
    }

    public int getReps() {
        return reps;
    }

    public int getGrade() {
        return grade;
    }

    public List<SessionEntity> getSessionEntities() {
        return sessionEntities;
    }

    public static class Builder {
        
        private int round;
        private int weight;
        private int distance;
        private int calories;
        private int reps;
        private int grade;
        private List<SessionEntity> sessionEntities;

        public Builder() {
            sessionEntities = new ArrayList<>();
        }

        public Builder setRound(int round) {
            this.round = round;
            return this;
        }

        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder setDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder setReps(int reps) {
            this.reps = reps;
            return this;
        }

        public Builder setGrade(int grade) {
            this.grade = grade;
            return this;
        }

        public Builder addSessionEntity(SessionEntity sessionEntity) {
            sessionEntities.add(sessionEntity);
            return this;
        }

        public ResultEntity build() {
            return new ResultEntity(round, weight, distance, calories, reps, grade, sessionEntities);
        }
    }
}
