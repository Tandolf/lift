package se.andolf.entities;

import se.andolf.api.Units;

/**
 * Created by Thomas on 2016-07-17.
 */
public class Body extends Entity{
    private Units unit;
    private int height;
    private int weight;

    public Body() {
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
