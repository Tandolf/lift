package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.workout.Workout;
import se.andolf.entities.ExerciseEntity;
import se.andolf.entities.WorkoutEntity;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.repository.ExerciseRepository;
import se.andolf.repository.UserRepository;
import se.andolf.repository.WorkoutRepository;
import se.andolf.utils.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public String save(Workout workout) {

        final WorkoutEntity workoutEntity = new WorkoutEntity.Builder()
                .user(workout.getUser())
                .date(workout.getDate())
                .description(workout.getDescription())
                .effort(workout.getEffort())
                .job(workout.getJob())
                .jobs(workout.getJobs())
                .build();

        return workoutRepository.save(workoutEntity).getId();
    }

    public ExerciseEntity getExerciseEntity(String id){
        return exerciseRepository.findOne(id);
    }

    public void delete(String id) {
        workoutRepository.delete(id);
    }

    public List<Workout> getAll(String id) {
        return workoutRepository.findByUser(id).stream().map(Mapper::toWorkout).collect(Collectors.toList());
    }

    public Workout find(String id) {
        return workoutRepository.findById(id).map(Mapper::toWorkout).orElseThrow(() -> new DocomentNotFoundException(String.format("Couldn't not find workout with id %s", id)));
    }

    public List<Workout> getAll(String id, LocalDate date) {
        return workoutRepository.findByDateAndUser(id, date).stream().map(Mapper::toWorkout).collect(Collectors.toList());
    }
}
