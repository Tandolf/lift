package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;

/**
 * Created by Thomas on 2016-06-11.
 */
public abstract class Entity {

    @GraphId
    private Long id;
    private String uniqueId;

    public Long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || id == null || getClass() != o.getClass())
            return false;

        Entity entity = (Entity) o;

        if (!id.equals(entity.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (id == null) ? -1 : id.hashCode();
    }
}
