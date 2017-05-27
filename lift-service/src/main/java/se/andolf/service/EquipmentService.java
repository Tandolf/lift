package se.andolf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.andolf.api.Equipment;
import se.andolf.entities.EquipmentEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.EquipmentRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Thomas on 2016-06-18.
 */
@Service
@Transactional
public class EquipmentService {

    private static Log LOG = LogFactory.getLog(EquipmentService.class);

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public long save(Equipment equipment) {

        final EquipmentEntity equipmentEntity = new EquipmentEntity(equipment.getName());
        try {
            return equipmentRepository.save(equipmentEntity).getId();
        } catch(ClientException e){
            LOG.info(e);
            throw new NodeExistsException("Equipment " + equipment.getName() + " exists please select another name");
        }
    }

    public List<Equipment> find() {
        return StreamSupport.stream(equipmentRepository.findAll().spliterator(), false).map(e -> toEquipment(e.getId(), e.getName())).collect(Collectors.toList());
    }

    public void delete(long id) {
        final Optional<EquipmentEntity> equipmentEntity = Optional.ofNullable(equipmentRepository.findOne(id));
        if(equipmentEntity.isPresent())
            equipmentRepository.delete(id);
        else
            throw new NodeNotFoundException("Could not find node with id " + id);
    }

    public void patch(JsonPatch jsonPatch, long id){
        final Optional<EquipmentEntity> equipmentEntity = Optional.ofNullable(equipmentRepository.findOne(id));
        if(equipmentEntity.isPresent()){

            final EquipmentEntity equipment = equipmentEntity.get();

            try {
                final JsonNode equipmentAsJsonString = objectMapper.valueToTree(equipment);
                final JsonNode patched = jsonPatch.apply(equipmentAsJsonString);
                final EquipmentEntity updatedEquipment = objectMapper.readValue(patched.toString(), EquipmentEntity.class);
                equipmentRepository.save(updatedEquipment);
            }  catch (IOException | JsonPatchException ex) {
                LOG.debug(ex);
                throw new IllegalArgumentException(ex.getMessage());
            }
        } else {
            throw new NodeNotFoundException("Could not find node with id " + id);
        }

    }

    public Equipment find(long id){
        final Optional<EquipmentEntity> equipmentEntity = Optional.ofNullable(equipmentRepository.findOne(id));
        if(equipmentEntity.isPresent())
            return new Equipment(equipmentEntity.get().getId(), equipmentEntity.get().getName());
        else {
            throw new NodeNotFoundException("Could not find equipment: " + id);
        }
    }

    private static Equipment toEquipment(long id, String name) {
        return new Equipment(id, name);
    }
}
