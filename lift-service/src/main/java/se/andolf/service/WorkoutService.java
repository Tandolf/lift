package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Exercise;
import se.andolf.api.Resistance;
import se.andolf.api.Workout;
import se.andolf.entities.*;
import se.andolf.repository.ExerciseRepository;
import se.andolf.repository.WorkoutRepository;

import java.util.List;
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

    public Long save(Workout workout) {

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

            final GroupEntity groupEntity = new GroupEntity();
            groupEntity.setValue(i);

            if (exercise.getExercises() != null) {
                IntStream.range(0, exercise.getExercises().size()).forEach(f -> {
                    final Exercise e = exercise.getExercises().get(f);
                    groupEntity.addExercise(new HasExerciseRel(f, groupEntity, getExerciseEntity(e.getId())));
                });
            }

            if (exercise.getId() != null){
                groupEntity.addExercise(new HasExerciseRel(0, groupEntity, getExerciseEntity(exercise.getId())));
            }

            groupEntity.setType(exercise.getType());
            workoutEntity.addGroup(groupEntity);
        });

        workout.getResistances().stream().forEach(resistance -> {
            final ResistanceEntity resistanceEntity = toResistanceEntity(resistance);
            final ExerciseEntity e = getExerciseEntity(resistance.getExerciseId());
            resistanceEntity.addExercise(e);
            workoutEntity.addResistanceRelation(resistanceEntity);
        });

        return workoutRepository.save(workoutEntity).getId();
    }

    private ResistanceEntity toResistanceEntity(Resistance resistance) {
        final ResistanceEntity resistanceEntity = new ResistanceEntity();
        resistanceEntity.setRepsFrom(resistance.getRepsFrom());
        resistanceEntity.setRepsTo(resistance.getRepsTo());
        resistanceEntity.setCalories(resistance.getCalories());
        resistanceEntity.setDistance(resistance.getDistance());
        resistanceEntity.setWeight(resistance.getWeight());
        resistanceEntity.setAlternateSides(resistance.isAlternateSides());
        resistanceEntity.setUnits(resistance.getUnits());
        resistanceEntity.isForCal(resistance.isForCal());
        resistanceEntity.isForDistance(resistance.isForDistance());
        return resistanceEntity;
    }

    public ExerciseEntity getExerciseEntity(long id){
        return exerciseRepository.findOne(id);
    }

    public void delete(long id) {
        workoutRepository.deleteWorkoutById(id);
    }

    public List<Workout> getAll() {
        return workoutRepository.findAllIdsAsList().stream().map(aLong -> new Workout.Builder().setId(aLong).build()).collect(Collectors.toList());
    }

    public Workout find(Long id) {
        return toWorkout(workoutRepository.findOne(id));
    }

    private Workout toWorkout(WorkoutEntity workout) {


        return new Workout.Builder().setId(workout.getId()).setDate(workout.getDate()).build();
    }
}
