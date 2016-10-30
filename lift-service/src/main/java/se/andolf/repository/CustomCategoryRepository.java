package se.andolf.repository;


import se.andolf.entities.Category;

/**
 * Created by Thomas on 2016-06-21.
 */
public interface CustomCategoryRepository {

    Category findByName(String name, int depth);

    Category findByUniqueId(String id, int depth);
}
