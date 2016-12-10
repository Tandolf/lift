package se.andolf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.neo4j.util.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.andolf.dto.CategoryDTO;
import se.andolf.dto.EquipmentDTO;
import se.andolf.entities.Equipment;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.EquipmentRepository;
import se.andolf.util.MappingUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Thomas on 2016-06-18.
 */
@Service
@Transactional
public class EquipmentService {

    private static Log LOG = LogFactory.getLog(EquipmentService.class);

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public EquipmentDTO save(EquipmentDTO newEquipment) {
        Equipment equipment = equipmentRepository.findByName(newEquipment.getName(), 1);
        if (equipment == null) {
            equipment = equipmentRepository.save(modelMapper.map(newEquipment, Equipment.class));
            return modelMapper.map(equipment, EquipmentDTO.class);
        }
        else
            throw new NodeExistsException("Equipment " + newEquipment.getName() + " exists please select another name");
    }

    public List<EquipmentDTO> getAll() {
        List<Equipment> equipment = IterableUtils.toList(equipmentRepository.findAll());
        return modelMapper.map(equipment, MappingUtils.getTypeAsList(CategoryDTO.class));
    }

    public void delete(String uniqueId) {
        try {
            Equipment equipment = equipmentRepository.findByUniqueId(uniqueId, 1);
            equipmentRepository.delete(equipment);
        } catch(DataRetrievalFailureException e){
            LOG.debug(e);
            throw new NodeNotFoundException("Could not find equipment: " + uniqueId);
        }
    }

    public EquipmentDTO loadById(String id) {
        final Equipment equipment = findByUniqueId(id);
        return modelMapper.map(equipment, EquipmentDTO.class);
    }

    public void patch(JsonPatch jsonPatch, String id){

        Equipment equipment = findByUniqueId(id);

        try {
            final JsonNode equipmentAsJsonString = objectMapper.valueToTree(equipment);
            final JsonNode patched = jsonPatch.apply(equipmentAsJsonString);
            equipment = objectMapper.readValue(patched.toString(), Equipment.class);
            equipmentRepository.save(equipment);
        }  catch (IOException | JsonPatchException ex) {
            LOG.debug(ex);
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private Equipment findByUniqueId(String id){
        try {
            return equipmentRepository.findByUniqueId(id, 1);
        } catch(DataRetrievalFailureException e){
            LOG.debug(e);
            throw new NodeNotFoundException("Could not find equipment: " + id);
        }
    }
}
