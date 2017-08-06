package se.andolf.utils;

import se.andolf.api.*;
import se.andolf.entities.*;

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
                .setResistances(Mapper.toResistance(workout.getResistanceEntities()))
                .setExercises(Mapper.toExercise(workout.getExerciseEntities()))
                .build();
    }

    public static List<Exercise> toExercise(List<ExerciseEntity> exerciseEntities) {
        if(exerciseEntities != null){
            exerciseEntities.sort((e1, e2) -> Integer.compare(e1.getOrder(), e2.getOrder()));
            return exerciseEntities.stream().map(Mapper::toExercise).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public static Exercise toExercise(ExerciseEntity exerciseEntity) {
        return new Exercise.Builder()
                .setId(exerciseEntity.getId())
                .setType(exerciseEntity.getType())
                .setName(exerciseEntity.getName())
                .setExercises(toExercise(exerciseEntity.getExerciseEntities()))
                .build();
    }

    public static List<Resistance> toResistance(List<ResistanceEntity> resistanceEntities) {
        final List<Resistance> resistances = new ArrayList<>();
        resistanceEntities.stream().forEach(r -> r.getExercises().stream().forEach(e -> {
            final Resistance.Builder builder = toResistance(r);
            resistances.add(builder.setExerciseId(e.getId()).build());
        }));

        return resistances;
    }

    public static Resistance.Builder toResistance(ResistanceEntity resistanceEntity) {
        return new Resistance.Builder()
                .setId(resistanceEntity.getId())
                .setWeight(resistanceEntity.getWeight())
                .setDistance(resistanceEntity.getDistance())
                .setCalories(resistanceEntity.getCalories())
                .setRepsFrom(resistanceEntity.getRepsFrom())
                .setRepsTo(resistanceEntity.getRepsTo())
                .isAlternateSides(resistanceEntity.isAlternateSides())
                .setForCal(resistanceEntity.isForCal())
                .setForDistance(resistanceEntity.isForDistance())
                .setUnits(resistanceEntity.getUnits())
                .setEffort(resistanceEntity.getEffort())
                .isStrapless(resistanceEntity.isStrapless())
                .setDamper(resistanceEntity.getDamper());
    }


    public static ResistanceEntity toResistanceEntity(Resistance resistance) {
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
        resistanceEntity.isStrapless(resistance.isStrapless());
        resistanceEntity.setEffort(resistance.getEffort());
        resistanceEntity.setDamper(resistance.getDamper());
        return resistanceEntity;
    }
}
