package se.andolf.entities;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by Thomas on 2016-06-11.
 */
@NodeEntity
public class Equipment extends Entity {

    public Equipment() { }

    private String name;

    @Relationship
    private Set<Image> images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
