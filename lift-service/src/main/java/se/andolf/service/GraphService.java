package se.andolf.service;

import java.util.List;

/**
 * Created by Thomas on 2016-06-26.
 */
public interface GraphService<T> {

    <S extends T> T save(S s);

    <S extends T> T load(String id);

    <T> List<T> getAll();

    void delete(String id);

    void update(T user);

    T getUserById(String userId);
}
