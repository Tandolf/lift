package se.andolf.repository;


import se.andolf.entities.User;

/**
 * Created by Thomas on 2016-06-26.
 */
public interface CustomUserRepository<T> {

    User findByName(String name, int depth);
    User findByUniqueId(String uniqueId, int depth);
    void delete(T entity);
}
