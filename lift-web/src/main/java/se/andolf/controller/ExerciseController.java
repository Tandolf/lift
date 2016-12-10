package se.andolf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.andolf.dto.ExerciseDTO;
import se.andolf.model.Equipment;
import se.andolf.model.Exercise;
import se.andolf.service.ExerciseService;
import se.andolf.util.MappingUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Thomas on 2016-06-12.
 */
@RestController
@Api(tags = "Exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = PUT, value="/exercise")
    @ApiOperation(value = "Add new exercise", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody @Valid Exercise restExcercise, HttpServletRequest request) throws URISyntaxException {
        ExerciseDTO exerciseDTO = exerciseService.save(modelMapper.map(restExcercise, ExerciseDTO.class));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().toString() + "/" + exerciseDTO.getUniqueId()));
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, value = "/exercise/{id}")
    @ApiOperation(value = "Gets equipment by name", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Exercise get(@PathVariable("id") String id){
        ExerciseDTO exerciseDTO = exerciseService.load(id);

        Equipment equipment = null;
        if (exerciseDTO.getEquipment() != null) {
            equipment = modelMapper.map(exerciseDTO.getEquipment(), Equipment.class);
        }
        Exercise restExercise = new Exercise(exerciseDTO.getName(), equipment);
        restExercise.setUniqueId(exerciseDTO.getUniqueId());

        return restExercise;
    }

    @RequestMapping(method = GET, value = "/exercise")
    @ApiOperation(value = "Gets all exercises as list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Exercise> get(){
        List<ExerciseDTO> exercises = exerciseService.getAll();
        List<Exercise> restExerciseList = modelMapper.map(exercises, MappingUtils.getTypeAsList(Exercise.class));
        return restExerciseList;
    }

    @RequestMapping(method = DELETE, value = "/exercise/{name}")
    @ApiOperation(value = "Delete equipment by name")
    public void delete(@PathVariable("name") String name){
        exerciseService.delete(name);
    }

}
