package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Result;
import se.andolf.entities.ResultEntity;
import se.andolf.entities.SessionEntity;
import se.andolf.exceptions.NodeNotFoundException;
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

    public void delete(long id) {

    }

    public Long save(Long sessionId, Result result){
        final Optional<SessionEntity> sessionEntity = Optional.ofNullable(sessionRepository.findOne(sessionId));
        if(!sessionEntity.isPresent())
            throw new NodeNotFoundException("No session with id: " + sessionId + " was found.");
        final ResultEntity resultEntity = new ResultEntity.Builder()
                .setRound(result.getRound())
                .setWeight(result.getWeight())
                .setDistance(result.getDistance())
                .setCalories(result.getCalories())
                .setReps(result.getReps())
                .setGrade(result.getGrade())
                .build();
        sessionEntity.get().addResultEntity(resultEntity);
        return sessionRepository.save(sessionEntity.get()).getId();
    }
}
