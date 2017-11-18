package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Workout;
import se.andolf.repository.WodRepository;
import se.andolf.utils.Mapper;

import java.util.List;

/**
 * @author Thomas on 2017-08-13.
 */
@Service
public class SessionService {

    private WodRepository wodRepository;

    @Autowired
    public SessionService(final WodRepository wodRepository) {
        this.wodRepository = wodRepository;
    }

    public List<Workout> getAll(String userId, String workoutId) {
        return Mapper.toWorkouts(wodRepository.findOne(workoutId).getWorkouts());
    }
}
