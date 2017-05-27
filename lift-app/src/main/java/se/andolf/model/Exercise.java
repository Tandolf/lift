package se.andolf.model;

import se.andolf.api.Equipment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Thomas on 2016-06-18.
 */
public class Exercise extends Entity {

    @NotNull
    @Size(min=2, max=30)
    private String name;
    private Equipment equipment;

    public Exercise() {
    }

    public Exercise(String name, Equipment equipment) {
        this.name = name;
        this.equipment = equipment;
    }

    public Exercise(String uniqueId, String name, Equipment equipment) {
        super(uniqueId);
        this.name = name;
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
