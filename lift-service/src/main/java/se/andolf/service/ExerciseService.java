package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.andolf.api.Exercise;
import se.andolf.entities.ExerciseEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author Thomas on 2016-06-18.
 */
@Service
@Transactional
public class ExerciseService {

    private Log log = LogFactory.getLog(ExerciseService.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    public Long save(Exercise exercise) {

        final ExerciseEntity exerciseEntity = new ExerciseEntity(exercise.getName());

        try {
            return exerciseRepository.save(exerciseEntity).getId();
        } catch (ClientException e){
            log.warn(e);
            throw new NodeExistsException("ExerciseEntity " + exercise.getName() + " exists please select another name");
        }
    }

    public List<Exercise> find() {
        return StreamSupport.stream(exerciseRepository.findAll().spliterator(), false).map(ExerciseService::toExercise).collect(Collectors.toList());
    }

    private static Exercise toExercise(ExerciseEntity exerciseEntity) {
        return new Exercise(exerciseEntity.getId(), exerciseEntity.getName());
    }

    public Exercise find(long id) {
        final Optional<ExerciseEntity> exerciseEntity = Optional.ofNullable(exerciseRepository.findOne(id));
        if(exerciseEntity.isPresent()){
            return toExercise(exerciseEntity.get());
        } else {
            throw new NodeNotFoundException("Could not find exercise with id: " + id);
        }
    }

    public void delete(long id) {
        final Optional<ExerciseEntity> exerciseEntity = Optional.ofNullable(exerciseRepository.findOne(id));
        if(exerciseEntity.isPresent()){
            exerciseRepository.delete(id);
        } else {
            throw new NodeNotFoundException("Could not find exercise with id: " +  id);
        }
    }
}
