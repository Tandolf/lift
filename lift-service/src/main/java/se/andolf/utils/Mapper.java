package se.andolf.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.andolf.api.*;
import se.andolf.api.user.Meta;
import se.andolf.api.user.User;
import se.andolf.entities.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2017-07-29.
 */
public final class Mapper {

    private Mapper() {
    }

    public static User toUser(UserEntity userEntity) {
        return new User.Builder()
                .setId(userEntity.getId())
                .meta(toMeta(userEntity.getMeta()))
                .setUserName(userEntity.getUserName())
                .name(userEntity.getName())
                .emails(userEntity.getEmails())
                .phoneNumbers(userEntity.getPhoneNumbers())
                .isActive(userEntity.isActive())
                .roles(userEntity.getRoles())
                .addresses(userEntity.getAddresses())
                .build();
    }

    private static Meta toMeta(MetaEntity meta) {
        return new Meta.Builder()
                .created(meta.getCreated())
                .lastModified(meta.getLastModified())
                .build();
    }

    public static UserEntity toUserEntity(User user) {
        return new UserEntity.Builder()
                .userName(user.getUserName())
                .isActive(user.isActive())
                .meta(toMetaEntity(user.getMeta()))
                .name(user.getName())
                .password(toEncrypted(user.getPassword()))
                .roles(user.getRoles())
                .build();
    }

    private static String toEncrypted(String password) {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);
        return bCryptPasswordEncoder.encode(password);
    }

    private static MetaEntity toMetaEntity(Meta meta) {
        return new MetaEntity.Builder()
                .lastModified(meta.getLastModified())
                .created(meta.getCreated())
                .build();
    }

    public static Wod toWod(WodEntity workout) {
        return new Wod.Builder()
                .setId(workout.getId())
                .date(workout.getDate().atZone(workout.getZoneId()))
                .workouts(Mapper.toWorkouts(workout.getWorkouts()))
                .build();
    }

    public static List<Workout> toWorkouts(List<WorkoutEntity> sessionEntities) {
        return sessionEntities.stream().map(Mapper::toWorkout).collect(Collectors.toList());
    }

    private static Workout toWorkout(WorkoutEntity workoutEntity) {
        return new Workout.Builder()
                .id(workoutEntity.getId())
                .job(workoutEntity.getJob())
                .alternating(workoutEntity.isAlternating())
                .routines(toRoutines(workoutEntity.getRoutines()))
                .build();
    }

    private static List<Routine> toRoutines(List<RoutineEntity> routines) {
        return routines.stream().map(Mapper::toRoutine).collect(Collectors.toList());
    }

    private static Routine toRoutine(RoutineEntity routineEntity) {
        return new Routine.Builder()
                .id(routineEntity.getId())
                .name(routineEntity.getName())
                .sessions(toSessions(routineEntity.getSessions()))
                .build();
    }

    private static List<Session> toSessions(List<SessionEntity> sessions) {
        return sessions.stream().map(Mapper::toSession).collect(Collectors.toList());
    }

    private static Session toSession(SessionEntity sessionEntity) {
        return new Session.Builder()
                .id(sessionEntity.getId())
                .exerciseId(sessionEntity.getExerciseId())
                .repsFrom(sessionEntity.getRepsFrom())
                .repsTo(sessionEntity.getRepsTo())
                .forDistance(sessionEntity.isForDistance())
                .forCal(sessionEntity.isForCal())
                .damper(sessionEntity.getDamper())
                .weight(sessionEntity.getWeight())
                .distance(sessionEntity.getDistance())
                .calories(sessionEntity.getCalories())
                .effort(sessionEntity.getEffort())
                .strapless(sessionEntity.isStrapless())
                .build();

    }

    public static Exercise toExercise(ExerciseEntity exerciseEntity) {
        return new Exercise.Builder()
                .setId(exerciseEntity.getId())
                .setName(exerciseEntity.getName())
                .setEquipments(exerciseEntity.getEquipments())
                .build();
    }

    public static ExerciseEntity toExerciseEntity(Exercise exercise) {
        return new ExerciseEntity.Builder().id(exercise.getId()).name(exercise.getName()).equipment(exercise.getEquipments()).build();
    }
}
