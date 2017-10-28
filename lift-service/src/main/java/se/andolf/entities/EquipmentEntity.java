package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Thomas on 2016-06-11.
 */
@Document(collection = "Equipment")
public class EquipmentEntity {

    @Id
    public String id;

    @Indexed(unique = true)
    private String name;

    @PersistenceConstructor
    public EquipmentEntity(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
