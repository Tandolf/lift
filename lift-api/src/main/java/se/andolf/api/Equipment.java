package se.andolf.api;

/**
 * @author Thomas on 2016-06-18.
 */
public class Equipment {

    private long id;
    private String name;

    public Equipment(String name) {
        this.name = name;
    }

    public Equipment(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
