package se.andolf.controller;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.Category;
import se.andolf.api.ErrorMessage;
import se.andolf.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Thomas on 2016-06-12.
 */
@RestController
@Api(tags = { "Categories" })
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Adds a new category", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CategoryEntity added", responseHeaders = {
                    @ResponseHeader(name = "Location", description = "path to the newly created resource", response = String.class)
            }),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessage.class)
    })
    @RequestMapping(method=PUT, value="/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> add(
            @RequestBody Category category, HttpServletRequest request) throws URISyntaxException {
        final Long id = categoryService.save(category);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + id));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets all the categories as a list", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessage.class)
    })
    @RequestMapping(method=GET, value="/categories")
    public List<Category> getAll(){
        return categoryService.find();
    }

    @ApiOperation(value = "Gets a category by id", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessage.class)
    })
    @RequestMapping(method=GET, value="/categories/{id}")
    public Category find(
            @ApiParam(value = "id of the category", required = true)
            @PathVariable("id") String id){
        return categoryService.find(Long.parseLong(id));
    }

    @ApiOperation(value = "Delete a category by id", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessage.class)
    })
    @RequestMapping(method=DELETE, value="/categories/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "id of the category that will find deleted", required = true)
            @PathVariable("id") String id){
        categoryService.delete(Long.parseLong(id));
    }

    @ApiOperation(value = "Patch category with RFC6902")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessage.class)
    })
    @RequestMapping(method=PATCH, value="/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable String id,
            @ApiParam(value = "Json format as RFC6902", required = true)
            @RequestBody JsonPatch patch){
        categoryService.patch(patch, Long.parseLong(id));
    }
}