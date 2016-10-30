package se.andolf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.dto.CategoryDTO;
import se.andolf.dto.PatchDTO;
import se.andolf.model.MessageEntity;
import se.andolf.model.Patch;
import se.andolf.model.RESTCategory;
import se.andolf.service.CategoryService;

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
@Api(tags = { "Categories" })
public class CategoryController {

    private static Log log = LogFactory.getLog(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "Adds a new category", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category added", responseHeaders = {
                    @ResponseHeader(name = "Location", description = "path to the newly created resource", response = String.class)
            }),
            @ApiResponse(code = 400, message = "Bad request", response = MessageEntity.class),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageEntity.class)
    })
    @RequestMapping(method=PUT, value="/category")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> add(
            @RequestBody @Valid RESTCategory restCategory, HttpServletRequest request) throws URISyntaxException {
        CategoryDTO categoryDTO = modelMapper.map(restCategory, CategoryDTO.class);
        categoryDTO = categoryService.save(categoryDTO);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + categoryDTO.getUniqueId()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets all the categories as a list", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = MessageEntity.class),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageEntity.class)
    })
    @RequestMapping(method=GET, value="/category")
    public List<RESTCategory> getAll(){
        List<CategoryDTO> categoriesDTO = categoryService.getAll();
        Type types = new TypeToken<List<RESTCategory>>() {}.getType();
        return modelMapper.map(categoriesDTO, types);
    }

    @ApiOperation(value = "Gets a category by id", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = MessageEntity.class),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageEntity.class)
    })
    @RequestMapping(method=GET, value="/category/{id}")
    public RESTCategory getById(
            @ApiParam(value = "id of the category", required = true)
            @PathVariable("id") String uniqueId){
        CategoryDTO categoryDTO = categoryService.findByUniqueId(uniqueId);
        return modelMapper.map(categoryDTO, RESTCategory.class);
    }

    @ApiOperation(value = "Delete a category by id", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = MessageEntity.class),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageEntity.class)
    })
    @RequestMapping(method=DELETE, value="/category/{id}")
    public void delete(
            @ApiParam(value = "id of the category that will get deleted", required = true)
            @PathVariable("id") String id ){
        categoryService.delete(id);
    }

    @ApiOperation(value = "Patch category with RFC6902")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = MessageEntity.class),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageEntity.class)
    })
    @RequestMapping(method=PATCH, value="/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable String id,
            @ApiParam(value = "Json format as RFC6902", required = true)
            @RequestBody List<Patch> patches){
        Type types = new TypeToken<List<PatchDTO>>() {}.getType();
        List<PatchDTO> patchDTOs = modelMapper.map(patches, types);
        categoryService.patch(patchDTOs, id);
    }
}