package se.andolf.configuration;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.event.BeforeSaveEvent;
import org.springframework.util.ReflectionUtils;
import se.andolf.entities.Entity;
import se.andolf.service.ExerciseService;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by Thomas on 2016-06-25.
 */
@Configuration
public class EventConfiguration {

    private static Log LOG = LogFactory.getLog(ExerciseService.class);

    private TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();

    @Bean
    ApplicationListener<BeforeSaveEvent> beforeSaveEventApplicationListener() {
        return event -> {
            if(event.getEntity() instanceof Entity) {
                Entity entity = (Entity) event.getEntity();
                if (entity.getUniqueId() == null){
                    setUUid(entity);
                }

                //TODO: recursive?
                for (Field field : entity.getClass().getDeclaredFields()) {
                    if(field.getType().getSuperclass() == Entity.class){
                        try {
                            field.setAccessible(true);
                            Entity nestedEntity = (Entity) field.get(entity);
                            if(nestedEntity != null){
                                if(nestedEntity.getUniqueId() == null){
                                    setUUid(nestedEntity);
                                }
                            } else {
                                LOG.debug("Field: " + field.getName() + " is null so no unique id is set");
                            }
                        } catch (IllegalAccessException e) {
                            LOG.error(e);
                        }
                    }
                }
            }
        };
    }

    private void setUUid(Object entity){
        final UUID uuid = timeBasedGenerator.generate();
        final StringBuilder sb = new StringBuilder();
        sb.append(Long.toHexString(uuid.getMostSignificantBits())).append(Long.toHexString(uuid.getLeastSignificantBits()));
        final String uuidAsString = sb.toString();
        final Field field = ReflectionUtils.findField(Entity.class, "uniqueId", String.class);
        field.setAccessible(true);
        ReflectionUtils.setField(field, entity, uuidAsString);
    }
}