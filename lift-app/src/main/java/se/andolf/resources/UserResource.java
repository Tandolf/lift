package se.andolf.resources;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.User;
import se.andolf.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Thomas on 2017-07-16.
 */
@RestController
@Api(tags = { "Users" })
public class UserResource {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Add a new User", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User added", responseHeaders = {
                    @ResponseHeader(name = "Location", description = "path to the newly created resource", response = String.class)
            }),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method = PUT, value="/users")
    public ResponseEntity add(@RequestBody User user, HttpServletRequest request) throws URISyntaxException {
        final long id = userService.save(user);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().append(id).toString()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method=DELETE, value="/users/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "id of the category that will find deleted", required = true)
            @PathVariable("id") String id){
        userService.delete(Long.parseLong(id));
    }

    @ApiOperation(value = "Get a list of all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned all workouts"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=GET, value="/users")
    public List<User> getAll(){
        return userService.getAll();
    }
}
