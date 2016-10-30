package se.andolf.service;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 2016-07-30.
 */
@Service
public class Neo4jDatabaseCleaner {

    @Autowired
    private Session session;

    public void cleanDb() {
        session.purgeDatabase();
    }
}
