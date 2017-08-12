package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.ExerciseSession;
import se.andolf.api.Workout;
import se.andolf.entities.*;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.ExerciseRepository;
import se.andolf.repository.UserRepository;
import se.andolf.repository.WorkoutRepository;
import se.andolf.utils.Mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Thomas on 2017-06-18.
 */
@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    public Long save(Long userId, Workout workout) {

        final UserEntity userEntity = userRepository.findOne(userId);

        final WorkoutEntity workoutEntity = new WorkoutEntity();
        workoutEntity.setWork(workout.getWork());
        workoutEntity.setRest(workout.getRest());
        workoutEntity.setSets(workout.getSets());
        workoutEntity.setDate(workout.getDate());
        workoutEntity.setEffort(workout.getEffort());
        workoutEntity.setAlternating(workout.isAlternating());
        workoutEntity.setWorkoutType(workout.getWorkoutType());
        workoutEntity.setExerciseSessionEntities(toExerciseSessionEntity(workout.getExerciseSessions()));
        workoutEntity.addUserEntity(userEntity);

        return workoutRepository.save(workoutEntity).getId();
    }

    public List<ExerciseSessionEntity> toExerciseSessionEntity(List<ExerciseSession> exerciseSessions) {
        if(exerciseSessions != null)
            return IntStream.range(0, exerciseSessions.size()).mapToObj(i -> {
                final ExerciseSession exerciseSession = exerciseSessions.get(i);
                final ExerciseSessionEntity exerciseSessionEntity = toExerciseSessionEntity(exerciseSession);
                exerciseSessionEntity.setOrder(i);
                if(exerciseSession.getExerciseId() != null)
                    exerciseSessionEntity.addExerciseEntity(exerciseRepository.findOne(exerciseSession.getExerciseId()));
                return exerciseSessionEntity;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }

    private ExerciseSessionEntity toExerciseSessionEntity(ExerciseSession exerciseSession) {
        final ExerciseSessionEntity exerciseSessionEntity = new ExerciseSessionEntity();
        exerciseSessionEntity.setWorkoutType(exerciseSession.getWorkoutType());
        exerciseSessionEntity.setAlternateSides(exerciseSession.isAlternateSides());
        exerciseSessionEntity.setCalories(exerciseSession.getCalories());
        exerciseSessionEntity.setDamper(exerciseSession.getDamper());
        exerciseSessionEntity.setDistance(exerciseSession.getDistance());
        exerciseSessionEntity.setEffort(exerciseSession.getEffort());
        exerciseSessionEntity.setForCal(exerciseSession.isForCal());
        exerciseSessionEntity.setRepsFrom(exerciseSession.getRepsFrom());
        exerciseSessionEntity.setRepsTo(exerciseSession.getRepsTo());
        exerciseSessionEntity.setStrapless(exerciseSession.isStrapless());
        exerciseSessionEntity.setForDistance(exerciseSession.isForDistance());
        exerciseSessionEntity.setUnits(exerciseSession.getUnits());
        exerciseSessionEntity.setWeight(exerciseSession.getWeight());
        if( exerciseSessionEntity.getExerciseSessionEntities() != null || exerciseSessionEntity.getExerciseSessionEntities().isEmpty())
            exerciseSessionEntity.setExerciseSessionEntities(toExerciseSessionEntity(exerciseSession.getExerciseSessions()));
        return exerciseSessionEntity;
    }

    public ExerciseEntity getExerciseEntity(long id){
        return exerciseRepository.findOne(id);
    }

    public void delete(long id) {
        workoutRepository.deleteWorkoutById(id);
    }

    public List<Workout> getAll() {
        return workoutRepository.findAllWorkoutsAsList().stream().map(workoutEntity -> new Workout.Builder().setId(workoutEntity.getId()).setDate(workoutEntity.getDate()).build()).collect(Collectors.toList());
    }

    public Workout find(Long id) {
        final Optional<WorkoutEntity> workoutEntity = Optional.ofNullable(workoutRepository.findById(id));
        if(workoutEntity.isPresent())
            return Mapper.toWorkout(workoutEntity.get());
        throw new NodeNotFoundException(String.format("Couldn't not find node with id %d", id));
    }

    public List<Workout> getAll(LocalDate date) {
        return workoutRepository.findByDate(date).stream().map(workoutEntity -> new Workout.Builder().setId(workoutEntity.getId()).setDate(workoutEntity.getDate()).build()).collect(Collectors.toList());
    }
}
