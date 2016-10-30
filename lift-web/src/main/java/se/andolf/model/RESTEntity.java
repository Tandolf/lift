package se.andolf.model;


/**
 * Created by Thomas on 2016-06-25.
 */
public abstract class RESTEntity {

    private String uniqueId;

    public RESTEntity() {
    }

    public RESTEntity(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
