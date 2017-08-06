package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Exercise;
import se.andolf.api.Workout;
import se.andolf.entities.*;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.ExerciseRepository;
import se.andolf.repository.UserRepository;
import se.andolf.repository.WorkoutRepository;
import se.andolf.utils.Mapper;

import java.time.LocalDate;
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
        workoutEntity.setWorkoutType(workout.getType());

        IntStream.range(0, workout.getExercises().size()).forEach(i -> {
            final Exercise exercise = workout.getExercises().get(i);
            final ExerciseEntity combinedExercises = new ExerciseEntity();

            if (exercise.getExercises() != null) {
                IntStream.range(0, exercise.getExercises().size()).forEach(f -> {
                    final Exercise e = exercise.getExercises().get(f);
                    final ExerciseEntity exerciseEntity = getExerciseEntity(e.getId());
                    exerciseEntity.setOrder(f);
                    combinedExercises.addExerciseEntity(exerciseEntity);
                });
            } else {
                final ExerciseEntity exerciseEntity = getExerciseEntity(exercise.getId());
                exerciseEntity.setOrder(0);
                combinedExercises.addExerciseEntity(exerciseEntity);
            }

            combinedExercises.setOrder(i);
            combinedExercises.setType(exercise.getType());
            workoutEntity.addExercise(combinedExercises);
        });

        workout.getResistances().stream().forEach(resistance -> {
            final ResistanceEntity resistanceEntity = Mapper.toResistanceEntity(resistance);
            final ExerciseEntity e = getExerciseEntity(resistance.getExerciseId());
            resistanceEntity.addExercise(e);
            workoutEntity.addResistanceRelation(resistanceEntity);
        });

        workoutEntity.addUserEntity(userEntity);

        return workoutRepository.save(workoutEntity).getId();
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
