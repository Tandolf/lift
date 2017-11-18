package se.andolf.resources;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.Wod;
import se.andolf.service.WodService;

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
@Api(tags = { "Wod" })
@RequestMapping("/users")
public class WodResource {

    @Autowired
    private WodService wodService;

    @ApiOperation(value = "Add a new Wod", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Wod added", responseHeaders = {
                    @ResponseHeader(name = "Location", description = "path to the newly created resource", response = String.class)
            }),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method = PUT, value="/wods")
    public ResponseEntity add(@PathVariable String userId, @RequestBody Wod wod, HttpServletRequest request) throws URISyntaxException {
        final String wodId = wodService.save(userId, wod);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().append("/").append(wodId).toString()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a Wod by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Wod deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=DELETE, value="/wods/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "id of the wod you want to delete", required = true)
            @PathVariable("id") String id){
        wodService.delete(id);
    }

    @ApiOperation(value = "Get a list of all wods")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned all Wods"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=GET, value="/wods")
    public List<Wod> getAll(Principal principal, @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        if(date != null)
            return wodService.getAll(date);

        return wodService.getAllForId(principal.getName());
    }

    @ApiOperation(value = "Get a Wod by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned Wod"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(method=GET, value="wods/{id}")
    public Wod getById(@PathVariable("id") String id){
        return wodService.find(id);
    }
}
