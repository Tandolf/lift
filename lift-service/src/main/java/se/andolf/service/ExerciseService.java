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

    private final static Log log = LogFactory.getLog(ExerciseService.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Long save(Exercise exercise) {
        return null;
//        final List<EquipmentEntity> equipmentEntities = new ArrayList<>();
//        if(exercise.getEquipments() != null && !exercise.getEquipments().isEmpty()){
//            exercise.getEquipments().stream().forEach(equipment -> {
//                final Optional<EquipmentEntity> equipmentEntity = Optional.ofNullable(equipmentRepository.findOne(equipment.getId()));
//                if (equipmentEntity.isPresent())
//                    equipmentEntities.add(equipmentEntity.get());
//                else {
//                    throw new DocomentNotFoundException("Could not find selected exercise with id " + equipment.getId());
//                }
//            });
//        }
//
//        final ExerciseEntity exerciseEntity = new ExerciseEntity(exercise.getName(), equipmentEntities);
//
//        try {
//            return exerciseRepository.save(exerciseEntity).getId();
//        } catch (Exception e){
//            log.warn(e);
//            throw new DocumentExistsException("ExerciseEntity " + exercise.getName() + " exists please select another name");
//        }
    }

    public List<Exercise> find() {
        return exerciseRepository.findAll().stream().map(Mapper::toExercise).collect(Collectors.toList());
    }

    public Exercise find(String id) {
        final Optional<ExerciseEntity> exerciseEntity = Optional.ofNullable(exerciseRepository.findOne(id));
        if(exerciseEntity.isPresent()){
            return Mapper.toExercise(exerciseEntity.get());
        } else {
            throw new DocomentNotFoundException("Could not find exercise with id: " + id);
        }
    }

    public void delete(String id) {
        final Optional<ExerciseEntity> exerciseEntity = Optional.ofNullable(exerciseRepository.findOne(id));
        if(exerciseEntity.isPresent()){
            exerciseRepository.delete(id);
        } else {
            throw new DocomentNotFoundException("Could not find exercise with id: " +  id);
        }
    }
}
