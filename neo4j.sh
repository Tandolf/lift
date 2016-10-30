#!/usr/bin/env bash
docker stop neo4j
docker rm neo4j
docker run -d --name neo4j --publish=7474:7474 --publish=7687:7687 --env=NEO4J_AUTH=neo4j/test neo4j:3.0
docker ps -a
