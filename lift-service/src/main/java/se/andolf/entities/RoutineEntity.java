package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

/**
 * @author Thomas on 2017-11-05.
 */
public class RoutineEntity {

    @Id
    private String id;
    private final String name;
    private final List<SessionEntity> sessions;

    @PersistenceConstructor
    public RoutineEntity(String id, String name, List<SessionEntity> sessions) {
        this.id = id;
        this.name = name;
        this.sessions = sessions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SessionEntity> getSessions() {
        return sessions;
    }
}
