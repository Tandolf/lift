package se.andolf.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.andolf.entities.Category;
import se.andolf.entities.Equipment;
import se.andolf.entities.Exercise;
import se.andolf.entities.User;
import se.andolf.repository.CategoryRepository;
import se.andolf.repository.UserRepository;
import se.andolf.service.Neo4jDatabaseCleaner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 2016-07-30.
 */
@Component
public class DatabaseTestUtils {

    private Log log = LogFactory.getLog(DatabaseTestUtils.class);

    @Autowired
    private Neo4jDatabaseCleaner cleaner;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private boolean created = true;

    public void clearAll(){
        if (!created)
            return;
        System.out.println("--- Clearing DB ---");
        cleaner.cleanDb();
    }

    public void createDb(){
        if(!created)
            return;
        log.info("--- Mocking DB ---");
        Category category = new Category();
        category.setName(TestData.CATEGORY.CURRENT.getName());
        ReflectionUtils.setField(category, "uniqueId", String.class, TestData.CATEGORY.CURRENT.getUniqueId());

        Exercise exercise = new Exercise();
        exercise.setName(TestData.EXERCISE.CURRENT.getName());
        ReflectionUtils.setField(exercise, "uniqueId", String.class, TestData.EXERCISE.CURRENT.getUniqueId());

        Equipment equipment = new Equipment();
        equipment.setName(TestData.EQUIPMENT.CURRENT.getName());
        ReflectionUtils.setField(equipment, "uniqueId", String.class, TestData.EQUIPMENT.CURRENT.getUniqueId());

        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);

        exercise.setEquipment(equipment);
        category.setExercises(exercises);

        User user = TestData.USER.CURRENT.getUser();

        userRepository.save(user);
        categoryRepository.save(category);
        created = true;
    }

    public void createDb(boolean created){
        this.created = created;
        createDb();
    }
}
