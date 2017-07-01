package se.andolf.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2016-06-18.
 */
public class Exercise {

    private long id;
    private String name;
    private List<Equipment> equipments;

    public Exercise(long id) {
        this.id = id;
    }

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(String name, List<Equipment> equipments) {
        this.name = name;
        this.equipments = equipments;
    }

    public Exercise(Long id, String name, List<Equipment> equipments) {
        this.id = id;
        this.name = name;
        this.equipments = equipments;
    }

    public Exercise(int id, String name, List<Equipment> equipments) {
        this.id = id;
        this.name = name;
        this.equipments = equipments;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public static class Builder {

        private String name;
        private List<Equipment> equipments;
        private int id;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder addEquipment(Equipment equipment) {

            if(equipments == null)
                equipments = new ArrayList<>();
            equipments.add(equipment);
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Exercise build() {
            return new Exercise(id, name, equipments);
        }
    }
}
