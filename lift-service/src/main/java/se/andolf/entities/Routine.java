package se.andolf.entities;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import se.andolf.api.ExerciseType;

import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 2016-06-26.
 */
public class Routine extends Entity {

    private String name;

    @DateLong
    private Date date;

    @Relationship(type = "IN")
    private List<Routine> routines;

    @Relationship(type = "IN")
    private List<In> exercises;

    @RelationshipEntity(type = "IN")
    public class In extends Entity{

        private ExerciseType EXERCISE_TYPE;

        public In(ExerciseType EXERCISE_TYPE, Routine startNode, Routine endNode) {
            this.EXERCISE_TYPE = EXERCISE_TYPE;
            this.startNode = startNode;
            this.endNode = endNode;
        }

        @StartNode
        private Routine startNode;

        @EndNode
        private Routine endNode;


        public ExerciseType getExerciseType() {
            return EXERCISE_TYPE;
        }

        public Routine getStartNode() {
            return startNode;
        }

        public Routine getEndNode() {
            return endNode;
        }
    }
}
