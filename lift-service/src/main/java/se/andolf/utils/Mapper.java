package se.andolf.utils;

import se.andolf.api.*;
import se.andolf.api.user.Meta;
import se.andolf.api.user.User;
import se.andolf.entities.*;
import se.andolf.service.EquipmentService;

import java.util.ArrayList;
import java.util.Comparator;
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
                .build();
    }

    private static Meta toMeta(MetaEntity meta) {
        return new Meta.Builder()
                .created(meta.getCreated())
                .lastModified(meta.getLastModified())
                .location(meta.getLocation())
                .build();
    }

    public static UserEntity toUserEntity(User user) {
        return new UserEntity.Builder()
                .userName(user.getUserName())
                .isActive(user.isActive())
                .build();
    }

    public static Workout toWorkout(WorkoutEntity workout) {
        return new Workout.Builder()
                .setId(workout.getId())
                .setDate(workout.getDate())
                .setWork(workout.getWork())
                .setSets(workout.getSets())
                .setRest(workout.getRest())
                .setEffort(workout.getEffort())
                .isAlternating(workout.isAlternating())
                .setSessions(Mapper.toSession(workout.getSessionEntities()))
                .build();
    }

    public static List<Session> toSession(List<SessionEntity> sessionEntities) {
        return sessionEntities.stream().sorted(Comparator.comparingInt(SessionEntity::getOrder)).map(Mapper::toSession).collect(Collectors.toList());
    }

    private static Session toSession(SessionEntity sessionEntity) {
        final Session.Builder builder = new Session.Builder();
        if(sessionEntity.getExerciseEntities() != null && !sessionEntity.getExerciseEntities().isEmpty()) {
            builder.setExerciseId(sessionEntity.getExerciseEntities().stream().findFirst().map(ExerciseEntity::getId).orElse(null));
        } else if (sessionEntity.getSessionEntities() != null && !sessionEntity.getSessionEntities().isEmpty())
            builder.setSessions(toSession(sessionEntity.getSessionEntities()));
        return builder.setId(sessionEntity.getId())
                .setUnits(sessionEntity.getUnits())
                .isAlternateSides(sessionEntity.isAlternateSides())
                .isStrapless(sessionEntity.isStrapless())
                .setCalories(sessionEntity.getCalories())
                .setForCal(sessionEntity.isForCal())
                .setEffort(sessionEntity.getEffort())
                .setForDistance(sessionEntity.isForDistance())
                .setDamper(sessionEntity.getDamper())
                .setDistance(sessionEntity.getDistance())
                .setRepsFrom(sessionEntity.getRepsFrom())
                .setRepsTo(sessionEntity.getRepsTo())
                .setWeight(sessionEntity.getWeight())
                .setWorkoutType(sessionEntity.getWorkoutType())
                .build();
    }

    public static Exercise toExercise(ExerciseEntity exerciseEntity) {
        final List<Equipment> equipments = new ArrayList<>();
        if(exerciseEntity.getEquipments() != null){
            exerciseEntity.getEquipments().stream().forEach(e -> equipments.add(EquipmentService.toEquipment(e)));
        }
        return new Exercise.Builder().setId(exerciseEntity.getId()).setName(exerciseEntity.getName()).setEquipments(equipments).build();
    }
}
