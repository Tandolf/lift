# Lift crossfit application &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[![Build Status](https://travis-ci.org/Tandolf/lift.svg?branch=master)](https://travis-ci.org/Tandolf/lift)

This repository contains the start of a spring boot application that is used to log crossfit workouts. It is backed up by a neo4j graph database and was developed to try out neo4j (https://neo4j.com).

### Running the application

clone the repo

```git clone https://github.com/Tandolf/lift.git```

run a neo4j with docker (or install neo4j locally) for the integration tests that are run during the build to pass (will be fixed so they are not run during the build when i have time :P i am lazy)

```docker run -d --publish=7474:7474 --publish=7687:7687 --env=NEO4J_AUTH=none --volume=$HOME/neo4j/data:/data --volume=$HOME/neo4j/logs:/logs```

build the project using maven

```mvn clean install```

run the jarfile buildt in the lift-app target folder

```jsvs -jar lift-app/target/lift-app-0.0.1-SNAPSHOT.jar```

### Swagger
When application is run there is a swagger that will show you the api, access the swagger at:
```http://localhost:8080/swagger-ui.html```

### Documentation
Usage of how to build workouts and explanation about the node-system of composing complicated workouts can be found in the wiki.
https://github.com/Tandolf/lift/wiki
