package se.andolf.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.andolf.model.RESTUser;
import se.andolf.dto.UserDTO;
import se.andolf.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Thomas on 2016-06-12.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "Adds a new user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(method=PUT, value="/user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created new resource", responseHeaders = @ResponseHeader(
                            name = "Location",
                            response = String.class,
                            description = "the location of the newly created resource")),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")

    })
    public ResponseEntity add(
            @ApiParam(value = "New user", required = true)
            @Valid @RequestBody RESTUser user, HttpServletRequest request) throws URISyntaxException {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO = userService.save(userDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + userDTO.getUniqueId()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get user by id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(method=GET, value="/user/{id}")
    public RESTUser getUserById(@PathVariable("id") String userId){
        UserDTO user = userService.getUserById(userId);
        return modelMapper.map(user, RESTUser.class);
    }

    @ApiOperation(value = "Gets all the users as a list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(method=GET, value="/user")
    public List<RESTUser> getAll(){
        List<UserDTO> users = userService.getAll();
        Type userListType = new TypeToken<List<RESTUser>>() {}.getType();
        return modelMapper.map(users, userListType);
    }

    @ApiOperation(value = "Delete a user by id")
    @RequestMapping(method=DELETE, value="/user/{id}")
    public void delete(
            @ApiParam(value = "user uniqueId", required = true)
            @PathVariable("id") String id ){
        userService.delete(id);
    }

    @RequestMapping(method=PATCH, value="/user/{id}")
    public void patch(@PathVariable String id, @RequestBody JsonPatch jsonPatch){
            userService.patch(jsonPatch, id);
    }
}
