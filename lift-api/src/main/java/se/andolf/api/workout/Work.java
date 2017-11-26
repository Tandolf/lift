package se.andolf.api.workout;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Thomas on 2017-11-26.
 */
public class Work {

    private String exerciseId;
    private Map<ExerciseParam, Object> parameters;

    private Work(String exerciseId, Map<ExerciseParam, Object> parameters) {
        this.exerciseId = exerciseId;
        this.parameters = parameters;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public Map<ExerciseParam, Object> getParameters() {
        return parameters;
    }

    public static final class Builder {

        private String exerciseId;
        private Map<ExerciseParam, Object> parameters = new EnumMap<>(ExerciseParam.class);

        public Builder exerciseId(String exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public Builder put(ExerciseParam key, Object value ) {
            this.parameters.put(key, value);
            return this;
        }

        public Work build() {
            return new Work(exerciseId, parameters);
        }
    }
}
