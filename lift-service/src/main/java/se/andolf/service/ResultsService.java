package se.andolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.Result;
import se.andolf.entities.ResultEntity;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.repository.ResultRepository;
import se.andolf.repository.WorkoutRepository;
import se.andolf.utils.Mapper;

/**
 * @author Thomas on 2017-08-27.
 */
@Service
public class ResultsService {

    private ResultRepository resultRepository;
    private WorkoutRepository workoutRepository;

    @Autowired
    public ResultsService(WorkoutRepository workoutRepository, ResultRepository resultRepository) {
        this.workoutRepository = workoutRepository;
        this.resultRepository = resultRepository;
    }

    public void delete(String id) {

    }

    public String save(String userId, String workout, Result result){
        return workoutRepository.findById(workout).map(w -> resultRepository.save(new ResultEntity.Builder()
                .user(userId)
                .workout(workout)
                .date(result.getDate())
                .reps(result.getReps())
                .build())
                .getId()
        ).orElseThrow(() ->new DocomentNotFoundException("No workout with id: " + workout + " was found."));
    }

    public Result find(String user, String id) {
        return resultRepository.findByUserAndId(user, id).map(Mapper::toResult).orElseThrow(() -> new DocomentNotFoundException("Could not find result with id: " + id));
    }
}
