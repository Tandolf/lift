package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Session;
import se.andolf.api.Workout;
import se.andolf.entities.ExerciseEntity;
import se.andolf.entities.SessionEntity;
import se.andolf.entities.UserEntity;
import se.andolf.entities.WorkoutEntity;
import se.andolf.exceptions.DocomentNotFoundException;
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

    public Long save(String userId, Workout workout) {

        final UserEntity userEntity = userRepository.findOne(userId);

        final WorkoutEntity workoutEntity = new WorkoutEntity();
        workoutEntity.setWork(workout.getWork());
        workoutEntity.setRest(workout.getRest());
        workoutEntity.setSets(workout.getSets());
        workoutEntity.setDate(workout.getDate());
        workoutEntity.setEffort(workout.getEffort());
        workoutEntity.setAlternating(workout.isAlternating());
        workoutEntity.setWorkoutType(workout.getWorkoutType());
        workoutEntity.setSessionEntities(toSessionEntity(workout.getSessions()));
        workoutEntity.addUserEntity(userEntity);

        return workoutRepository.save(workoutEntity).getId();
    }

    public List<SessionEntity> toSessionEntity(List<Session> sessions) {
        if(sessions != null)
            return IntStream.range(0, sessions.size()).mapToObj(i -> {
                final Session session = sessions.get(i);
                final SessionEntity sessionEntity = toSessionEntity(session);
                sessionEntity.setOrder(i);
                if(session.getExerciseId() != null)
                    sessionEntity.addExerciseEntity(exerciseRepository.findOne(session.getExerciseId()));
                return sessionEntity;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }

    private SessionEntity toSessionEntity(Session session) {
        final SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setWorkoutType(session.getWorkoutType());
        sessionEntity.setAlternateSides(session.isAlternateSides());
        sessionEntity.setCalories(session.getCalories());
        sessionEntity.setDamper(session.getDamper());
        sessionEntity.setDistance(session.getDistance());
        sessionEntity.setEffort(session.getEffort());
        sessionEntity.setForCal(session.isForCal());
        sessionEntity.setRepsFrom(session.getRepsFrom());
        sessionEntity.setRepsTo(session.getRepsTo());
        sessionEntity.setStrapless(session.isStrapless());
        sessionEntity.setForDistance(session.isForDistance());
        sessionEntity.setUnits(session.getUnits());
        sessionEntity.setWeight(session.getWeight());
        if( sessionEntity.getSessionEntities() != null || sessionEntity.getSessionEntities().isEmpty())
            sessionEntity.setSessionEntities(toSessionEntity(session.getSessions()));
        return sessionEntity;
    }

    public ExerciseEntity getExerciseEntity(String id){
        return exerciseRepository.findOne(id);
    }

    public void delete(String id) {
        workoutRepository.delete(id);
    }

    public List<Workout> getAll() {
        return workoutRepository.findAll().stream().map(workoutEntity -> new Workout.Builder().setId(workoutEntity.getId()).setDate(workoutEntity.getDate()).build()).collect(Collectors.toList());
    }

    public Workout find(String id) {
        final Optional<WorkoutEntity> workoutEntity = Optional.ofNullable(workoutRepository.findOne(id));
        if(workoutEntity.isPresent())
            return Mapper.toWorkout(workoutEntity.get());
        throw new DocomentNotFoundException(String.format("Couldn't not find node with id %d", id));
    }

    public List<Workout> getAll(LocalDate date) {
        return workoutRepository.findByDate(date).stream().map(workoutEntity -> new Workout.Builder().setId(workoutEntity.getId()).setDate(workoutEntity.getDate()).build()).collect(Collectors.toList());
    }
}
