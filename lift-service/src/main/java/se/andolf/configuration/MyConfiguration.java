package se.andolf.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import se.andolf.service.ExerciseService;

/**
 * Created by Thomas on 2016-06-06.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "se.andolf")
@EnableTransactionManagement
public class MyConfiguration extends Neo4jConfiguration {

    private static Log LOG = LogFactory.getLog(ExerciseService.class);

    @Bean
    public SessionFactory getSessionFactory(){
        LOG.debug("Loading session factory");
        return new SessionFactory("se.andolf");
    }

}
