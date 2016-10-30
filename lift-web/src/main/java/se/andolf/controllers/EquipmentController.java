package se.andolf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.andolf.dto.EquipmentDTO;
import se.andolf.model.RESTEquipment;
import se.andolf.service.EquipmentService;
import se.andolf.util.MappingUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Thomas on 2016-06-18.
 */
@RestController
@Api(tags = "Equipment")
public class EquipmentController {

    private Log log = LogFactory.getLog(EquipmentController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method=PUT, value="/equipment")
    @ApiOperation(value = "Adds a new piece of equipment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody @Valid RESTEquipment restEquipment, HttpServletRequest request) throws URISyntaxException {
        EquipmentDTO equipment = modelMapper.map(restEquipment, EquipmentDTO.class);
        equipment = equipmentService.save(equipment);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + equipment.getUniqueId()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method=GET, value="/equipment")
    @ApiOperation(value = "Gets all equipment as a list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<RESTEquipment> getAll(){
        List<EquipmentDTO> equipment = equipmentService.getAll();
        return modelMapper.map(equipment, MappingUtils.getTypeAsList(RESTEquipment.class));
    }

    @RequestMapping(method=GET, value="/equipment/{id}")
    @ApiOperation(value = "Gets single equipment by id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RESTEquipment getById(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable String id){
        EquipmentDTO equipment = equipmentService.loadById(id);
        return modelMapper.map(equipment, RESTEquipment.class);
    }

    @RequestMapping(method=DELETE, value="/equipment/{id}")
    @ApiOperation(value = "Deletes the selected equipment")
    public void delete(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable("id") String id){
        equipmentService.delete(id);
    }

    @RequestMapping(method=PATCH, value="/equipment/{id}")
    @ApiOperation(value = "Patch equipment with RFC6902")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(
            @ApiParam(value = "uniqueId of equipment", required = true)
            @PathVariable String id,
            @ApiParam(value = "Json format as RFC6902", required = true)
            @Valid @RequestBody JsonPatch patch){
        equipmentService.patch(patch, id);
    }
}
