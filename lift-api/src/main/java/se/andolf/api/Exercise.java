package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2016-06-18.
 */
public class Exercise {

    private long id;
    private String name;
    private List<Equipment> equipments;

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(String name, List<Equipment> equipments) {
        this.name = name;
        this.equipments = equipments;
    }

    public Exercise(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exercise(Long id, String name, List<Equipment> equipments) {
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

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
