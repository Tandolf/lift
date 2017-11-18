package se.andolf.entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Thomas on 2017-10-28.
 */
public class MetaEntity {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime created;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime lastModified;

    public MetaEntity(LocalDateTime created, LocalDateTime lastModified) {
        this.created = created;
        this.lastModified = lastModified;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public static final class Builder {

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

        public MetaEntity build() {
            return new MetaEntity(created, lastModified);
        }
    }
}
