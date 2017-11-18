package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Result;
import se.andolf.entities.ResultEntity;
import se.andolf.entities.WorkoutEntity;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.repository.ResultRepository;
import se.andolf.repository.SessionRepository;

import java.util.Optional;

/**
 * @author Thomas on 2017-08-27.
 */
@Service
public class ResultsService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public void delete(String id) {

    }

    public String save(String sessionId, Result result){
        final Optional<WorkoutEntity> sessionEntity = Optional.ofNullable(sessionRepository.findOne(sessionId));
        if(!sessionEntity.isPresent())
            throw new DocomentNotFoundException("No session with id: " + sessionId + " was found.");
        final ResultEntity resultEntity = new ResultEntity.Builder()
                .setRound(result.getRound())
                .setWeight(result.getWeight())
                .setDistance(result.getDistance())
                .setCalories(result.getCalories())
                .setReps(result.getReps())
                .setGrade(result.getGrade())
                .build();
        return sessionRepository.save(sessionEntity.get()).getId();
    }
}
