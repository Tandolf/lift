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
import se.andolf.api.Category;
import se.andolf.entities.CategoryEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.CategoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author by Thomas on 2016-06-11.
 */
@Service
@Transactional
public class CategoryService {

    private final static Log LOG = LogFactory.getLog(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Long save(Category category){
        final CategoryEntity categoryEntity = new CategoryEntity(category.getName());

        try {
            return categoryRepository.save(categoryEntity).getId();
        } catch (ClientException e) {
            LOG.error("Category " + category.getName() + " exists select another name", e);
            throw new NodeExistsException("Category " + category.getName() + " exists please select another name");
        }
    }

    public List<Category> find() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false).map(CategoryService::toCategory).collect(Collectors.toList());
    }

    public void delete(long id) {
        final Optional<CategoryEntity> categoryEntity = Optional.ofNullable(categoryRepository.findOne(id));
        if(categoryEntity.isPresent())
            categoryRepository.delete(id);
        else
            throw new NodeNotFoundException("Could not find node with id " + id);
    }

    public Category find(long id) {
        final Optional<CategoryEntity> categoryEntity = Optional.ofNullable(categoryRepository.findOne(id));
        if(categoryEntity.isPresent())
            return new Category(categoryEntity.get().getId(), categoryEntity.get().getName());
        else {
            throw new NodeNotFoundException("Could not find equipment: " + id);
        }
    }

    public void patch(JsonPatch patch, long id){

        final Optional<CategoryEntity> categoryEntity = Optional.ofNullable(categoryRepository.findOne(id));
        if(!categoryEntity.isPresent())
            throw new NodeNotFoundException("Could not find categoryEntity with id: " + id);
        else {

            final CategoryEntity category = categoryEntity.get();

            try {
                final JsonNode categoryAsJsonString = objectMapper.valueToTree(category);
                final JsonNode patched = patch.apply(categoryAsJsonString);
                if(patched != null){
                    final CategoryEntity updatedCategory = objectMapper.readValue(patched.toString(), CategoryEntity.class);
                    categoryRepository.save(updatedCategory);
                }
            }  catch (IOException | JsonPatchException ex) {
                LOG.debug(ex);
                throw new IllegalArgumentException(ex.getMessage());
            }
        }

    }

    private static Category toCategory(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getId(), categoryEntity.getName());
    }
}
