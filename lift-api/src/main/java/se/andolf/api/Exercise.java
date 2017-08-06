package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2016-06-18.
 */
    public class Exercise {

    private final Long id;
    private final String name;
    private final List<Equipment> equipments;
    private final List<Exercise> exercises;
    private final WorkoutType type;

    public Exercise(Long id, String name, List<Equipment> equipments, List<Exercise> exercises, WorkoutType type) {
        this.id = id;
        this.name = name;
        this.equipments = equipments;
        this.exercises = exercises;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public WorkoutType getType() {
        return type;
    }

    public static class Builder {

        private Long id;
        private String name;
        private List<Equipment> equipments;
        private List<Exercise> exercises;
        private WorkoutType type;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setExercises(List<Exercise> exercises) {
            this.exercises = exercises;
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
            return new Exercise(id, name, equipments, exercises, type);
        }
    }
}
