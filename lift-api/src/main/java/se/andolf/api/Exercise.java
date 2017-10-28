package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2016-06-18.
 */
    public class Exercise {

    private final String id;
    private final String name;
    private final List<Equipment> equipments;
    private final WorkoutType type;

    public Exercise(String id, String name, List<Equipment> equipments, WorkoutType type) {
        this.id = id;
        this.name = name;
        this.equipments = equipments;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public WorkoutType getType() {
        return type;
    }

    public static class Builder {

        private String id;
        private String name;
        private List<Equipment> equipments;
        private WorkoutType type;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setType(WorkoutType type) {
            this.type = type;
            return this;
        }

        public Builder setEquipments(List<Equipment> equipments) {
            this.equipments = equipments;
            return this;
        }

        public Exercise build() {
            return new Exercise(id, name, equipments, type);
        }
    }
}
