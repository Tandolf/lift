package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.*;
import se.andolf.entities.*;
import se.andolf.repository.ExerciseRepository;
import se.andolf.repository.WodRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Thomas on 2017-06-18.
 */
@Service
public class WodService {

    @Autowired
    private WodRepository wodRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public Long save(Wod wod) {

        final WodEntity wodEntity = new WodEntity();
        wodEntity.setDate(wod.getDate());
        wodEntity.setName(wod.getName());

        if(wod.getWorkouts() != null){
            final List<ContainsWorkoutRelEntity> containsWorkoutRelEntities = IntStream.range(0, wod.getWorkouts().size()).mapToObj(i -> {

                final Workout workout = wod.getWorkouts().get(i);
                final List<GroupEntity> groupEntities = new ArrayList<>();

                if(workout.getGroups() != null){
                    final List<Group> groups = workout.getGroups();
                    IntStream.range(0, groups.size()).forEach(j -> {
                        final Group group = groups.get(j);

                        final List<ResistanceEntity> resistanceEntities = getResistanceEntities(group);

                        groupEntities.add(new GroupEntity(j, resistanceEntities));

                    });
                }

                final WorkoutEntity workoutEntity = toWorkoutEntity(workout);
                workoutEntity.setGroupEntites(groupEntities);

                return new ContainsWorkoutRelEntity(i, wodEntity, workoutEntity);
            }).collect(Collectors.toList());

            wodEntity.setContainsWorkoutRelEntities(containsWorkoutRelEntities);
        }

        return wodRepository.save(wodEntity).getId();
    }

    private List<ResistanceEntity> getResistanceEntities(Group group) {

        final List<ResistanceEntity> resistanceEntites = new ArrayList<>();

        group.getExercises().stream().forEach(exercise -> IntStream.range(0, group.getResistance().size()).forEach(i -> {
            final Resistance resistance = group.getResistance().get(i);
            if (exercise.getId() == resistance.getExerciseId()) {
                final ResistanceEntity resistanceEntity = toResistanceEntity(resistance);
                final ExerciseEntity exerciseEntity = getExerciseEntity(exercise.getId());
                resistanceEntity.setResistanceToExerciseRel(new ResistanceToExerciseRel(i, resistanceEntity, exerciseEntity));
                resistanceEntites.add(resistanceEntity);
            }
        }));

        return resistanceEntites;
    }

    private ResistanceEntity toResistanceEntity(Resistance resistance) {
        return new ResistanceEntity(resistance.getWeight(),
                resistance.getDistance(),
                resistance.getCalories(),
                resistance.getRepsFrom(),
                resistance.getRepsTo(),
                resistance.isAltLeftRight(),
                resistance.getUnits());
    }

    private WorkoutEntity toWorkoutEntity(Workout workout) {
        return new WorkoutEntity(workout.getWork(),
                workout.getRest(),
                workout.getSets(),
                workout.getWorkoutType(),
                workout.isAlternating());
    }

    public ExerciseEntity getExerciseEntity(long id){
        return exerciseRepository.findOne(id);
    }

    public void delete(long id) {
        wodRepository.deleteWodById(id);
    }

    public List<Wod> getAll() {
        return wodRepository.findAllIdsAsList().stream().map(aLong -> new Wod.Builder().setId(aLong).build()).collect(Collectors.toList());
    }

    public Wod find(Long id) {
        return toWod(wodRepository.findOne(id));
    }

    private Wod toWod(WodEntity wodEntity) {
        return new Wod.Builder().setId(wodEntity.getId()).setDate(wodEntity.getDate()).build();
    }
}
