package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Equipment;
import se.andolf.api.Exercise;
import se.andolf.entities.EquipmentEntity;
import se.andolf.entities.ExerciseEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.EquipmentRepository;
import se.andolf.repository.ExerciseRepository;
import se.andolf.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author Thomas on 2016-06-18.
 */
@Service
public class ExerciseService {

    private final static Log log = LogFactory.getLog(ExerciseService.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Long save(Exercise exercise) {

        final List<EquipmentEntity> equipmentEntities = new ArrayList<>();
        if(exercise.getEquipments() != null && !exercise.getEquipments().isEmpty()){
            exercise.getEquipments().stream().forEach(equipment -> {
                final Optional<EquipmentEntity> equipmentEntity = Optional.ofNullable(equipmentRepository.findOne(equipment.getId()));
                if (equipmentEntity.isPresent())
                    equipmentEntities.add(equipmentEntity.get());
                else {
                    throw new NodeNotFoundException("Could not find selected exercise with id " + equipment.getId());
                }
            });
        }

        final ExerciseEntity exerciseEntity = new ExerciseEntity(exercise.getName(), equipmentEntities);

        try {
            return exerciseRepository.save(exerciseEntity).getId();
        } catch (ClientException e){
            log.warn(e);
            throw new NodeExistsException("ExerciseEntity " + exercise.getName() + " exists please select another name");
        }
    }

    public List<Exercise> find() {
        return StreamSupport.stream(exerciseRepository.findAll().spliterator(), false).map(Mapper::toExercise).collect(Collectors.toList());
    }

    public Exercise find(long id) {
        final Optional<ExerciseEntity> exerciseEntity = Optional.ofNullable(exerciseRepository.findOne(id));
        if(exerciseEntity.isPresent()){
            return Mapper.toExercise(exerciseEntity.get());
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
