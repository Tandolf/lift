package se.andolf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value="Category")
public class CategoryController {

    private static Log log = LogFactory.getLog(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation(value = "Adds a new category", produces = "application/json")
    @RequestMapping(method=PUT, value="/category")
    public ResponseEntity add(
            @RequestBody @Valid RESTCategory restCategory,
            HttpServletRequest request) throws URISyntaxException {
        CategoryDTO categoryDTO = modelMapper.map(restCategory, CategoryDTO.class);
        categoryDTO = categoryService.save(categoryDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + categoryDTO.getUniqueId()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets all the categories as a list", produces = "application/json")
    @RequestMapping(method=GET, value="/category")
    public List<RESTCategory> getAll(){
        List<CategoryDTO> categoriesDTO = categoryService.getAll();
        Type types = new TypeToken<List<RESTCategory>>() {}.getType();
        return modelMapper.map(categoriesDTO, types);
    }

    @ApiOperation(value = "Gets a category by id", produces = "application/json")
    @RequestMapping(method=GET, value="/category/{id}")
    public RESTCategory getById(
            @ApiParam(value = "id of the category", required = true)
            @PathVariable("id") String uniqueId){
        CategoryDTO categoryDTO = categoryService.findByUniqueId(uniqueId);
        return modelMapper.map(categoryDTO, RESTCategory.class);
    }

    @ApiOperation(value = "Delete a category by id", produces = "application/json")
    @RequestMapping(method=DELETE, value="/category/{id}")
    public void delete(
            @ApiParam(value = "id of the category that will get deleted", required = true)
            @PathVariable("id") String id ){
        categoryService.delete(id);
    }

    @RequestMapping(method=PATCH, value="/category/{id}")
    @ApiOperation(value = "Patch category with RFC6902")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable String id,
            @ApiParam(value = "Json format as RFC6902", required = true)
            @RequestBody JsonPatch patch){
        categoryService.patch(patch, id);
    }
}