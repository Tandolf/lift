package se.andolf.resources;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.Result;
import se.andolf.service.ResultsService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Thomas on 2017-08-05.
 */
@RestController
@Api(tags = { "Workouts" })
@RequestMapping("/users")
public class ResultsResource {

    @Autowired
    private ResultsService resultsService;

    @ApiOperation(value = "Add a new result", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Result added", responseHeaders = {
                    @ResponseHeader(name = "Location", description = "path to the newly created resource", response = String.class)
            }),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method = PUT, value="{userId}/workouts/{workoutId}/sessions/{sessionId}")
    public ResponseEntity add(@PathVariable("sessionId") String sessionId, @RequestBody Result result, HttpServletRequest request) throws URISyntaxException {
        final String id = resultsService.save(sessionId, result);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().append("/").append(id).toString()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

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
          resultsService.delete(id);
    }
}
