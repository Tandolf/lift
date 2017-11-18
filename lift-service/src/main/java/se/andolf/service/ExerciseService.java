package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Exercise;
import se.andolf.entities.ExerciseEntity;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.repository.EquipmentRepository;
import se.andolf.repository.ExerciseRepository;
import se.andolf.utils.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Thomas on 2016-06-18.
 */
@Service
public class ExerciseService {

    private static final Log log = LogFactory.getLog(ExerciseService.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    public String save(Exercise exercise) {
        return exerciseRepository.save(Mapper.toExerciseEntity(exercise)).getId();
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
}
