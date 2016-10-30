package se.andolf.dto;

/**
 * Created by Thomas on 2016-07-03.
 */
public abstract class BaseDTO {

    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
