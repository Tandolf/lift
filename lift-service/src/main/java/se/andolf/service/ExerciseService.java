package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import se.andolf.api.Exercise;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.exceptions.DocumentExistsException;
import se.andolf.repository.ExerciseRepository;
import se.andolf.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Thomas on 2016-06-18.
 */
@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public String save(Exercise exercise) {
        try {
            return exerciseRepository.save(Mapper.toExerciseEntity(exercise)).getId();
        } catch (DuplicateKeyException e) {
            throw new DocumentExistsException("Category " + exercise.getName() + " exists please select another name", e);
        }
    }

    public List<Exercise> find() {
        return exerciseRepository.findAll().stream().map(Mapper::toExercise).collect(Collectors.toList());
    }

    public Exercise find(String id) {
        return exerciseRepository.findById(id).map(Mapper::toExercise).orElseThrow(() -> new DocomentNotFoundException("Could not find exercise with id: " + id));
    }

    public void delete(String id) {
        exerciseRepository.delete(id);
    }

    public List<Exercise> findFilteredBy(String name) {
        return exerciseRepository.findByNameContainingIgnoreCase(name).stream().map(Mapper::toExercise).collect(Collectors.toList());
    }
}
