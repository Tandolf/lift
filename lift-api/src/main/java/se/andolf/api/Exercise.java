package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2016-06-18.
 */
    public class Exercise {

    private final String id;
    private final String name;
    private final List<String> equipments;

    public Exercise(String id, String name, List<String> equipments) {
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

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setEquipments(List<String> equipments) {
            this.equipments = equipments;
            return this;
        }

        public Exercise build() {
            return new Exercise(id, name, equipments);
        }
    }
}
