package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.neo4j.util.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import se.andolf.repository.EquipmentRepository;
import se.andolf.dto.ExerciseDTO;
import se.andolf.entities.Exercise;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.ExerciseRepository;
import se.andolf.util.MappingUtils;

import java.util.List;


/**
 * Created by Thomas on 2016-06-18.
 */
@Service
@Transactional
public class ExerciseService {

    private Log log = LogFactory.getLog(ExerciseService.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ExerciseDTO save(ExerciseDTO exerciseDTO) {
        Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
        try {
            exerciseRepository.findByName(exercise.getName(), 1);
        } catch (DataRetrievalFailureException e){
            log.debug(e);
        } catch (IllegalArgumentException e){
            log.debug(e);
            throw new NodeExistsException("Exercise " + exercise.getName() + " exists please select another name");
        }

        exercise = exerciseRepository.save(exercise);
        return modelMapper.map(exercise, ExerciseDTO.class);
    }

    public ExerciseDTO load(String id) {
        Exercise exercise;
        try {
            exercise = exerciseRepository.findOne(Long.parseLong(id));
        } catch (DataRetrievalFailureException e){
            log.warn(e);
            throw new NodeNotFoundException("Could not find exercise with id: " + id);
        }
        return modelMapper.map(exercise, ExerciseDTO.class);
    }

    public void delete(String id) {
        Exercise exercise;
        try {
            exercise = exerciseRepository.findOne(Long.parseLong(id));
            exerciseRepository.delete(exercise);
        } catch (DataRetrievalFailureException e){
            throw new NodeNotFoundException("Could not find exercise with id: " +  id);
        }
    }

    public List<ExerciseDTO> getAll() {
        List<Exercise> exercises =IterableUtils.toList(exerciseRepository.findAll());
        return modelMapper.map(exercises, MappingUtils.getTypeAsList(ExerciseDTO.class));
    }
}
