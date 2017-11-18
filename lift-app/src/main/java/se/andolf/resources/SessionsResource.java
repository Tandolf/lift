package se.andolf.resources;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.andolf.api.Workout;
import se.andolf.service.SessionService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Thomas on 2017-08-13.
 */
@RestController
@Api(tags = { "Sessions" })
@RequestMapping("/users")
public class SessionsResource {

    @Autowired
    private SessionService sessionService;

    @ApiOperation(value = "Delete a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "fetch workouts successfull"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=GET, value="{userId}/workouts/{workoutId}/sessions")
    public List<Workout> get(
            @PathVariable("userId") String userId,
            @ApiParam(value = "Id of the workout you want workouts from", required = true)
            @PathVariable("workoutId") String workoutId){
        return sessionService.getAll(userId, workoutId);
    }
}
