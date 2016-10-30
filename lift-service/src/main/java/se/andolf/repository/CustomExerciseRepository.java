package se.andolf.repository;


import se.andolf.entities.Exercise;

/**
 * Created by Thomas on 2016-06-21.
 */
public interface CustomExerciseRepository {

    Exercise findByName(String name, int depth);
    Exercise findByUniqueId(String id, int depth);
}
