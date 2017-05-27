package se.andolf.controller;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.api.Equipment;
import se.andolf.service.EquipmentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author by Thomas on 2016-06-18.
 */
@RestController
@Api(tags = "Equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(method=PUT, value="/equipments")
    @ApiOperation(value = "Adds a new piece of equipment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody Equipment equipment, HttpServletRequest request) throws URISyntaxException {
        final long id = equipmentService.save(equipment);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + id));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method=GET, value="/equipments")
    @ApiOperation(value = "Gets all equipment as a list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Equipment> getAll(){
        return equipmentService.find();
    }

    @RequestMapping(method=GET, value="/equipments/{id}")
    @ApiOperation(value = "Gets single equipment by id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Equipment getById(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable String id){
        return equipmentService.find(Long.parseLong(id));

    }

    @RequestMapping(method=DELETE, value="/equipments/{id}")
    @ApiOperation(value = "Deletes the selected equipment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable("id") String id){
        equipmentService.delete(Long.parseLong(id));
    }

    @RequestMapping(method=PATCH, value="/equipments/{id}")
    @ApiOperation(value = "Patch equipment with RFC6902")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable String id,
            @ApiParam(value = "Json format as RFC6902", required = true)
            @Valid @RequestBody JsonPatch patch){
        equipmentService.patch(patch, Long.parseLong(id));
    }
}
