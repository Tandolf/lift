package se.andolf.api.user;

import java.time.LocalDateTime;

/**
 * @author Thomas on 2017-10-28.
 */
public class Meta {

    private final LocalDateTime created;
    private final LocalDateTime lastModified;

    public Meta(LocalDateTime created, LocalDateTime lastModified) {
        this.created = created;
        this.lastModified = lastModified;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public static class Builder {

        private LocalDateTime created;
        private LocalDateTime lastModified;
        public Builder created(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Meta build() {
            return new Meta(created, lastModified);
        }
    }
}
