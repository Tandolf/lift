package se.andolf.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.Exercise;
import se.andolf.service.ExerciseService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Thomas on 2016-06-12.
 */
@RestController
@Api(tags = "Exercises")
public class ExerciseResource {

    @Autowired
    private ExerciseService exerciseService;

    @RequestMapping(method = PUT, value="/exercises")
    @ApiOperation(value = "Add new exercise", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody Exercise exercise, HttpServletRequest request) throws URISyntaxException {
        final long id = exerciseService.save(exercise);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().append("/").append(id).toString()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, value = "/exercises/{id}")
    @ApiOperation(value = "Gets equipment by name", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Exercise find(@PathVariable("id") String id){
        return exerciseService.find(id);
    }

    @RequestMapping(method = GET, value = "/exercises")
    @ApiOperation(value = "Gets all exercises as list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Exercise> getAll(){
        return exerciseService.find();
    }

    @RequestMapping(method = DELETE, value = "/exercises/{name}")
    @ApiOperation(value = "Delete equipment by name")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("name") String id){
        exerciseService.delete(id);
    }
}
