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
    private final String location;

    public MetaEntity(LocalDateTime created, LocalDateTime lastModified, String location) {
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
}
