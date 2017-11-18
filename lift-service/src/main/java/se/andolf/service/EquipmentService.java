package se.andolf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.andolf.api.Equipment;
import se.andolf.entities.EquipmentEntity;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.exceptions.DocumentExistsException;
import se.andolf.repository.EquipmentRepository;
import se.andolf.repository.ExerciseRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2016-06-18.
 */
@Service
@Transactional
public class EquipmentService {

    private static final Log LOG = LogFactory.getLog(EquipmentService.class);

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String save(Equipment equipment) {

        final EquipmentEntity equipmentEntity = new EquipmentEntity(equipment.getName());
        try {
            return equipmentRepository.save(equipmentEntity).getId();
        } catch(DuplicateKeyException e){
            LOG.info(e);
            throw new DocumentExistsException("Equipment " + equipment.getName() + " exists please select another name");
        }
    }

    public List<Equipment> find() {
        return equipmentRepository.findAll().stream().map(EquipmentService::toEquipment).collect(Collectors.toList());
    }

    public void delete(String id) {
        equipmentRepository.delete(id);
        exerciseRepository.deleteAllEquipments(id);
    }

    public void patch(JsonPatch jsonPatch, String id){
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
            throw new DocomentNotFoundException("Could not find node with id " + id);
        }

    }

    public Equipment find(String id){
        return Optional.ofNullable(equipmentRepository.findOne(id))
                .map(equipmentEntity -> new Equipment(equipmentEntity.getId(), equipmentEntity.getName()))
                .orElseThrow(DocomentNotFoundException::new);
    }

    public static Equipment toEquipment(EquipmentEntity equipmentEntity) {
        return new Equipment(equipmentEntity.getId(), equipmentEntity.getName());
    }
}
