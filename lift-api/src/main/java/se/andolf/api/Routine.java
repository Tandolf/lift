package se.andolf.api;

import java.util.List;

/**
 * @author Thomas on 2017-11-05.
 */
public class Routine {

    private final String id;
    private final String name;
    private final List<Session> sessions;

    public Routine(String id, String name, List<Session> sessions) {
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

    public List<Session> getSessions() {
        return sessions;
    }

    public static class Builder {

        private String id;
        private String name;
        private List<Session> sessions;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder sessions(List<Session> sessions) {
            this.sessions = sessions;
            return this;
        }

        public Routine build() {
            return new Routine(id, name, sessions);
        }
    }
}
