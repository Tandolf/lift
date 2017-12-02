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
import se.andolf.api.Category;
import se.andolf.entities.CategoryEntity;
import se.andolf.exceptions.DocomentNotFoundException;
import se.andolf.exceptions.DocumentExistsException;
import se.andolf.repository.CategoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author by Thomas on 2016-06-11.
 */
@Service
@Transactional
public class CategoryService {

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public String save(Category category){
        final CategoryEntity categoryEntity = new CategoryEntity(category.getName());

        try {
            return categoryRepository.save(categoryEntity).getId();
        } catch (DuplicateKeyException e) {
            throw new DocumentExistsException("Category " + category.getName() + " exists please select another name", e);
        }
    }

    public List<Category> find() {
        return categoryRepository.findAll().stream()
                .map(CategoryService::toCategory)
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        categoryRepository.delete(id);
    }

    public Category find(String id) {
        return Optional.ofNullable(categoryRepository.findOne(id))
                .map(categoryEntity -> new Category(categoryEntity.getId(), categoryEntity.getName()))
                .orElseThrow(DocomentNotFoundException::new);
    }

    public void patch(JsonPatch patch, String id){

        final Optional<CategoryEntity> categoryEntity = Optional.ofNullable(categoryRepository.findOne(id));
        if(!categoryEntity.isPresent())
            throw new DocomentNotFoundException("Could not find categoryEntity with id: " + id);
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
                throw new IllegalArgumentException(ex.getMessage());
            }
        }

    }

    private static Category toCategory(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getId(), categoryEntity.getName());
    }
}
