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
                .setSessions(Mapper.toSession(workout.getSessionEntities()))
                .build();
    }

    private static List<Session> toSession(List<SessionEntity> sessionEntities) {
        return sessionEntities.stream().sorted((o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder())).map(Mapper::toSession).collect(Collectors.toList());
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
