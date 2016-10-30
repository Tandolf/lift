package se.andolf.dto;

/**
 * Created by Thomas on 2016-07-03.
 */
public class ExerciseDTO extends BaseDTO {

    private String name;
    private EquipmentDTO equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentDTO getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDTO equipment) {
        this.equipment = equipment;
    }
}
