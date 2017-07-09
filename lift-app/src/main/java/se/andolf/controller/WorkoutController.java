package se.andolf.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.Workout;
import se.andolf.service.WorkoutService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Thomas on 2017-06-18.
 */
@RestController
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @RequestMapping(method = PUT, value="/workouts")
    @ApiOperation(value = "Add new wod", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody Workout workout, HttpServletRequest request) throws URISyntaxException {
        final long id = workoutService.save(workout);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + id));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method=DELETE, value="/workouts/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "id of the Wod you want to delete", required = true)
            @PathVariable("id") String id){
        workoutService.delete(Long.parseLong(id));
    }

    @RequestMapping(method=GET, value="/workouts")
    public Object getAll(){
        return workoutService.getAll();
    }

    @RequestMapping(method=GET, value="/workouts/{id}")
    public Object getById(@PathVariable("id") Long id){
        return workoutService.find(id);
    }
}
