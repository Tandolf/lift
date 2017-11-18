package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2016-06-11.
 */
@Document(collection = "Exercise")
public class ExerciseEntity {

    @Id
    private String id;
    private String name;
    private List<String> equipments = new ArrayList<>();

    @PersistenceConstructor
    public ExerciseEntity(String id, String name, List<String> equipments) {
        this.id = id;
        this.name = name;
        this.equipments = equipments;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public static class Builder {

        private String id;
        private String name;
        private List<String> equipments;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder equipment(List<String> equipments) {
            this.equipments = equipments;
            return this;
        }

        public ExerciseEntity build() {
            return new ExerciseEntity(id, name, equipments);
        }
    }
}
