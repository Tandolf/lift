package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Workout;
import se.andolf.api.Wod;
import se.andolf.entities.ExerciseEntity;
import se.andolf.entities.WorkoutEntity;
import se.andolf.entities.UserEntity;
import se.andolf.entities.WodEntity;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.repository.ExerciseRepository;
import se.andolf.repository.UserRepository;
import se.andolf.repository.WodRepository;
import se.andolf.utils.Mapper;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2017-06-18.
 */
@Service
public class WodService {

    @Autowired
    private WodRepository wodRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    public String save(String userId, Wod wod) {

        final UserEntity userEntity = userRepository.findOne(userId);

        final WodEntity wodEntity = new WodEntity.Builder().build();

        return wodRepository.save(wodEntity).getId();
    }

    public List<WorkoutEntity> toSessionEntity(List<Workout> sessions) {

        return new ArrayList<>();
    }

    private WorkoutEntity toSessionEntity(Workout session) {

        return null;
    }

    public ExerciseEntity getExerciseEntity(String id){
        return exerciseRepository.findOne(id);
    }

    public void delete(String id) {
        wodRepository.delete(id);
    }

    public List<Wod> getAllForId(String id) {
        return wodRepository.findByUsersContaining(id).stream().map(wodEntity -> new Wod.Builder().setId(wodEntity.getId()).build()).collect(Collectors.toList());
    }

    public Wod find(String id) {
        return wodRepository.findById(id).map(Mapper::toWod).orElseThrow(() -> new DocomentNotFoundException(String.format("Couldn't not find node with id %s", id)));
    }

    public List<Wod> getAll(LocalDate date) {
        return wodRepository.findByDate(date).stream().map(wodEntity -> new Wod.Builder().setId(wodEntity.getId()).build()).collect(Collectors.toList());
    }
}
