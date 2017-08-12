package se.andolf.utils;

import se.andolf.api.*;
import se.andolf.entities.*;
import se.andolf.service.EquipmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
                .setFirstname(userEntity.getFirstname())
                .setLastname(userEntity.getLastname())
                .setAccountInfo(toAccountInfo(userEntity.getAccountInfoEntities()))
                .setContactInfo(toContactInfo(userEntity.getContactInfoEntities()))
                .build();
    }

    public static ContactInfo toContactInfo(Set<ContactInfoEntity> contactInfoEntities) {
        return contactInfoEntities.stream().findFirst().map(Mapper::toContactInfo).orElse(null);
    }

    public static AccountInfo toAccountInfo(Set<AccountInfoEntity> accountInfoEntities) {
        return accountInfoEntities.stream().findFirst().map(Mapper::toAccountInfo).orElse(null);
    }

    public static AccountInfo toAccountInfo(AccountInfoEntity accountInfoEntity) {
        return new AccountInfo.Builder().setEmail(accountInfoEntity.getEmail()).build();
    }

    public static ContactInfo toContactInfo(ContactInfoEntity contactInfoEntity) {
        return new ContactInfo.Builder().
                setAddressLine1(contactInfoEntity.getAddressLine1())
                .setAddressLine2(contactInfoEntity.getAddressLine2())
                .setCity(contactInfoEntity.getCity())
                .setPostalCode(contactInfoEntity.getPostalCode())
                .setStateOrProvince(contactInfoEntity.getStateOrProvince())
                .setMobileNumber(contactInfoEntity.getMobileNumber())
                .build();
    }


    public static UserEntity toUserEntity(User user) {
        return new UserEntity.Builder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .addAccountInfo(toAccountInfoEntity(user.getAccountInfo()))
                .addContactInfo(toContactInfoEntity(user.getContactInfo()))
                .build();
    }

    public static ContactInfoEntity toContactInfoEntity(ContactInfo contactInfo) {
        return new ContactInfoEntity.Builder()
                .setAddressLine1(contactInfo.getAddressLine1())
                .setAddressLine2(contactInfo.getAddressLine2())
                .setCity(contactInfo.getCity())
                .setStateOrProvince(contactInfo.getStateOrProvince())
                .setPostalCode(contactInfo.getPostalCode())
                .setMobileNumber(contactInfo.getMobileNumber()).build();
    }

    public static AccountInfoEntity toAccountInfoEntity(AccountInfo accountInfo) {
        return new AccountInfoEntity.Builder().setEmail(accountInfo.getEmail()).build();
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
                .setExerciseSessions(Mapper.toExerciseSession(workout.getExerciseSessionEntities()))
                .build();
    }

    private static List<ExerciseSession> toExerciseSession(List<ExerciseSessionEntity> exerciseSessionEntities) {
        return exerciseSessionEntities.stream().sorted((o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder())).map(Mapper::toExerciseSession).collect(Collectors.toList());
    }

    private static ExerciseSession toExerciseSession(ExerciseSessionEntity exerciseSessionEntity) {
        final ExerciseSession.Builder builder = new ExerciseSession.Builder();
        if(exerciseSessionEntity.getExerciseEntities() != null && !exerciseSessionEntity.getExerciseEntities().isEmpty()) {
            builder.setExerciseId(exerciseSessionEntity.getExerciseEntities().stream().findFirst().map(ExerciseEntity::getId).orElse(null));
        } else if (exerciseSessionEntity.getExerciseSessionEntities() != null && !exerciseSessionEntity.getExerciseSessionEntities().isEmpty())
            builder.setExerciseSessions(toExerciseSession(exerciseSessionEntity.getExerciseSessionEntities()));
        return builder.setId(exerciseSessionEntity.getId())
                .setUnits(exerciseSessionEntity.getUnits())
                .isAlternateSides(exerciseSessionEntity.isAlternateSides())
                .isStrapless(exerciseSessionEntity.isStrapless())
                .setCalories(exerciseSessionEntity.getCalories())
                .setForCal(exerciseSessionEntity.isForCal())
                .setEffort(exerciseSessionEntity.getEffort())
                .setForDistance(exerciseSessionEntity.isForDistance())
                .setDamper(exerciseSessionEntity.getDamper())
                .setDistance(exerciseSessionEntity.getDistance())
                .setRepsFrom(exerciseSessionEntity.getRepsFrom())
                .setRepsTo(exerciseSessionEntity.getRepsTo())
                .setWeight(exerciseSessionEntity.getWeight())
                .setWorkoutType(exerciseSessionEntity.getWorkoutType())
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
