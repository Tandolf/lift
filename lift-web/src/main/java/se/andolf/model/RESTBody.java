package se.andolf.model;

import org.hibernate.validator.constraints.Length;
import se.andolf.api.Units;

import javax.validation.constraints.NotNull;

/**
 * Created by Thomas on 2016-07-17.
 */
public class RESTBody {

    @NotNull
    private Units unit;

    @NotNull
    @Length(min = 2, max = 64)
    private int height;

    @NotNull
    @Length(min = 2, max = 64)
    private int weight;

    public RESTBody() {
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
