CREATE (adam:User { name: 'Adam' }),(pernilla:User { name: 'Pernilla' }),(david:User { name: 'David'}),(adam)-[:FRIEND]->(pernilla),(pernilla)-[:FRIEND]->(david);
MATCH (n) OPTIONAL MATCH (n)-[r]-() RETURN n,r LIMIT 100;
