package se.andolf.utils;

import org.junit.Test;
import se.andolf.api.Exercise;
import se.andolf.entities.ExerciseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Thomas on 2017-08-06.
 */
public class MapperTest {

    @Test
    public void shouldMapListOfExercisesToListOfExercisesEntities() {
//        final List<Exercise> exercises = IntStream.range(0, 5).mapToObj(i -> {
//            final List<Exercise> collect = IntStream.range(0, 3).mapToObj(j -> new Exercise.Builder().setName(Integer.toString(j)).build()).collect(Collectors.toList());
//            return new Exercise.Builder().setExercises(collect).build();
//        }).collect(Collectors.toList());
//
//        List<ExerciseEntity> exerciseEntities = Mapper.toExerciseEntity(exercises);
//        assertEquals(exerciseEntities.size(), 5);
//        assertEquals(exerciseEntities.get(0).getExerciseEntities().size(), 3);
//        assertEquals(exerciseEntities.get(0).getExerciseEntities().get(0).getOrder(), 0);
//        assertEquals(exerciseEntities.get(0).getExerciseEntities().get(1).getOrder(), 1);
    }
}
