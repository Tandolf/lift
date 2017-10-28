package se.andolf.api.user;

import java.time.LocalDateTime;

/**
 * @author Thomas on 2017-10-28.
 */
public class Meta {

    private final LocalDateTime created;
    private final LocalDateTime lastModified;
    private final String location;

    public Meta(LocalDateTime created, LocalDateTime lastModified, String location) {
        this.created = created;
        this.lastModified = lastModified;
        this.location = location;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public String getLocation() {
        return location;
    }

    public static class Builder {

        private LocalDateTime created;
        private LocalDateTime lastModified;
        private String location;

        public Builder created(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Meta build() {
            return new Meta(created, lastModified, location);
        }
    }
}
