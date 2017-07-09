package se.andolf.api;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @author Thomas on 2017-06-24.
 */
public class Group {

    private List<Long> ids;
    private WorkoutType type;

    public Group(List<Long> ids, WorkoutType type) {
        this.ids = ids;
        this.type = type;
    }

    public List<Long> getIds() {
        return ids;
    }

    public WorkoutType getType() {
        return type;
    }
}
