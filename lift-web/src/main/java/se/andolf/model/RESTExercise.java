package se.andolf.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Thomas on 2016-06-18.
 */
public class RESTExercise extends RESTEntity {

    @NotNull
    @Size(min=2, max=30)
    private String name;
    private RESTEquipment equipment;

    public RESTExercise() {
    }

    public RESTExercise(String name, RESTEquipment equipment) {
        this.name = name;
        this.equipment = equipment;
    }

    public RESTExercise(String uniqueId, String name, RESTEquipment equipment) {
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

    public RESTEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(RESTEquipment equipment) {
        this.equipment = equipment;
    }
}
