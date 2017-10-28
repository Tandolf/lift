package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import se.andolf.api.WorkoutType;

/**
 * @author Thomas on 2017-06-23.
 */
@Document(collection = "Work")
public class WorkEntity {

    @Id
    private Long id;
    private int work;
    private int rest;
    private int sets;
    private WorkoutType type;

    @PersistenceConstructor
    public WorkEntity(int work, int rest, int sets, WorkoutType type) {
        this.work = work;
        this.rest = rest;
        this.sets = sets;
        this.type = type;
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

    public WorkoutType getType() {
        return type;
    }

    public void setType(WorkoutType type) {
        this.type = type;
    }
}
