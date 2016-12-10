package se.andolf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.diff.JsonDiff;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.util.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import se.andolf.dto.PatchDTO;
import se.andolf.entities.Category;
import se.andolf.entities.Equipment;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.CategoryRepository;
import se.andolf.dto.CategoryDTO;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.util.MappingUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Thomas on 2016-06-11.
 */
@Service
@Transactional
public class CategoryService {

    private static Log LOG = LogFactory.getLog(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public CategoryDTO save(CategoryDTO newCategory){
        Category category = modelMapper.map(newCategory, Category.class);

        try {
            Assert.isNull(categoryRepository.findByName(category.getName(), 1));
            category = categoryRepository.save(category);
            return modelMapper.map(category, CategoryDTO.class);
        } catch (IllegalArgumentException e) {
            LOG.error("Category " + newCategory.getName() + " exists please select another name", e);
            throw new NodeExistsException("Category " + newCategory.getName() + " exists please select another name");
        }
    }

    public List<CategoryDTO> getAll() {
        final List<Category> categories = IterableUtils.toList(categoryRepository.findAll());
        return modelMapper.map(categories, MappingUtils.getTypeAsList(CategoryDTO.class));
    }

    public void delete(String uniqueId) {
        final Category category = categoryRepository.findByUniqueId(uniqueId, 1);
        if (category != null)
            categoryRepository.delete(category);
        else
            throw new NodeNotFoundException("Could not find category: " + uniqueId);
    }

    public CategoryDTO findByUniqueId(String id) {
        final Category category = categoryRepository.findByUniqueId(id, 1);
        if (category != null) {
            return modelMapper.map(category, CategoryDTO.class);
        }
        else {
            throw new NodeNotFoundException("Could not find category with id: " + id);
        }
    }

    public void patch(JsonPatch patch, String id){

        Category category = categoryRepository.findByUniqueId(id, 1);
        if(category == null)
            throw new NodeNotFoundException("Could not find category with id: " + id);

        try {
            final JsonNode categoryAsJsonString = objectMapper.valueToTree(category);
            final JsonNode patched = patch.apply(categoryAsJsonString);
            if(patched != null){
                category = objectMapper.readValue(patched.toString(), Category.class);
                categoryRepository.save(category);
            }
        }  catch (IOException | JsonPatchException ex) {
            LOG.debug(ex);
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
