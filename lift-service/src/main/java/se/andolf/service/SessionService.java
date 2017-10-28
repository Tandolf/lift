package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Session;
import se.andolf.repository.SessionRepository;
import se.andolf.repository.WorkoutRepository;
import se.andolf.utils.Mapper;

import java.util.List;

/**
 * @author Thomas on 2017-08-13.
 */
@Service
public class SessionService {

    private WorkoutRepository workoutRepository;

    @Autowired
    public SessionService(final WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<Session> getAll(String userId, String workoutId) {
        return Mapper.toSession(workoutRepository.findOne(workoutId).getSessionEntities());
    }
}
