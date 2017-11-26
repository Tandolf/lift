package se.andolf.resources;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.workout.Workout;
import se.andolf.service.WorkoutService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Thomas on 2017-06-18.
 */
@RestController
@Api(tags = { "Workouts" })
@RequestMapping("/users")
public class WorkoutResource {

    @Autowired
    private WorkoutService workoutService;

    @ApiOperation(value = "Add a new workout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "workout added", responseHeaders = {
                    @ResponseHeader(name = "Location", description = "path to the newly created resource", response = String.class)
            }),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value="/workouts")
    public ResponseEntity add(@RequestBody Workout workout, HttpServletRequest request) throws URISyntaxException {
        final String wodId = workoutService.save(workout);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().append("/").append(wodId).toString()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a Wod by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "workout deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=DELETE, value="/workouts/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "id of the wod you want to delete", required = true)
            @PathVariable("id") String id){
        workoutService.delete(id);
    }

    @ApiOperation(value = "Get a list of all workouts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned all workouts"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=GET, value="/workouts")
    public List<Workout> getAll(Principal principal, @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        if(date != null)
            return workoutService.getAll(principal.getName(), date);

        return workoutService.getAll(principal.getName());
    }

    @ApiOperation(value = "Get a workouts by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned workout"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=GET, value="workouts/{id}")
    public Workout getById(@PathVariable("id") String id){
        return workoutService.find(id);
    }
}
