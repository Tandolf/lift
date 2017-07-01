package se.andolf.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import se.andolf.entities.WodEntity;

import java.util.List;

/**
 * @author Thomas on 2017-06-18.
 */
public interface WodRepository extends Neo4jRepository<WodEntity, Long> {

    @Query("MATCH (wod) WHERE ID(wod)={id} " +
            "MATCH path=(wod)-[*..5]->(resistance:ResistanceEntity) " +
            "foreach (r in relationships(path) | delete r) " +
            "foreach (n in nodes(path) | detach delete n)")
    void deleteWodById(long id);

    @Query("MATCH (w:WodEntity) RETURN ID(w)")
    List<Long> findAllIdsAsList();
}
