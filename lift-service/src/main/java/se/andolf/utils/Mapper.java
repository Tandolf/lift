package se.andolf.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.andolf.api.Exercise;
import se.andolf.api.Result;
import se.andolf.api.user.Meta;
import se.andolf.api.user.User;
import se.andolf.api.workout.Workout;
import se.andolf.entities.*;

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
                .timeZone(userEntity.getTimezone())
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
                .timezone(user.getTimezone())
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

    public static Workout toWorkout(WorkoutEntity workoutEntity) {
        return new Workout.Builder()
                .id(workoutEntity.getId())
                .user(workoutEntity.getUser())
                .date(workoutEntity.getDate())
                .description(workoutEntity.getDescription())
                .effort(workoutEntity.getEffort())
                .job(workoutEntity.getJob())
                .jobs(workoutEntity.getJobs())
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

    public static Result toResult(ResultEntity resultEntity) {
        return new Result.Builder()
                .id(resultEntity.getId())
                .workout(resultEntity.getWorkout())
                .date(resultEntity.getDate())
                .sets(resultEntity.getReps())
                .build();
    }
}
