package se.andolf.api;

/**
 * @author Thomas on 2016-06-18.
 */
public class Exercise {

    private long id;
    private String name;
    private Equipment equipment;

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(String name, Equipment equipment) {
        this.name = name;
        this.equipment = equipment;
    }

    public Exercise(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
