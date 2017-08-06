package se.andolf.resources;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * @author Thomas on 2017-08-05.
 */
@RestController
@Api(tags = { "Workouts" })
@RequestMapping("/users")
public class ResultsResource {

//    @Autowired
//    private ResultsService resultsService;

    @ApiOperation(value = "Delete a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Result deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=DELETE, value="{userId}/workouts/{workoutId}/results/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("userId") String userId,
            @ApiParam(value = "id of the Wod you want to delete", required = true)
            @PathVariable("id") String id){
//        resultsService.delete(Long.parseLong(id));
    }
}
