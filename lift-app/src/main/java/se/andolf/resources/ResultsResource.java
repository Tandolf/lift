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
import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Thomas on 2017-08-05.
 */
@RestController
@Api(tags = { "results" })
@RequestMapping("/users/workouts/{workout}/results")
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
    @RequestMapping(method = POST)
    public ResponseEntity add(@PathVariable("workout") String workout, @RequestBody Result result, HttpServletRequest request, Principal principal) throws URISyntaxException {
        final String userId = principal.getName();
        final String id = resultsService.save(userId, workout, result);
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
    @RequestMapping(method=DELETE, value="{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "id of the Wod you want to delete", required = true)
            @PathVariable("id") String id){
          resultsService.delete(id);
    }

    @ApiOperation(value = "Get a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Result fetched"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=GET, value="{id}")
    public Result find(
            @ApiParam(value = "id of the result you want to fetch", required = true)
            @PathVariable("id") String id, Principal principal){
        return resultsService.find(principal.getName(), id);
    }
}
