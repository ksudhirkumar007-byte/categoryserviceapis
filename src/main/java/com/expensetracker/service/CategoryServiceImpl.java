package com.expensetracker.service;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.model.Category;
import com.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements  CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category savedCategory = categoryRepository.findById(id).get();
        return convertToDTO(savedCategory);
    }

    @Override
    public boolean deleteCategory(Long id) {
        try{
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CategoryDTO updateCategory(Long id,CategoryDTO category) {
        Category category1 = categoryRepository.findById(id).get();
        
        if(!category.getName().equalsIgnoreCase(category1.getName())){
            category1.setName(category.getName());
        }
        if(!category.getType().equalsIgnoreCase(category1.getType())){
            category1.setType(category.getType());
        }

        if(category.getBudget()!=(category1.getBudget())){
            category1.setBudget(category.getBudget());
        }
        Category updatedCategory = categoryRepository.save(category1);
        return convertToDTO(updatedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    private CategoryDTO convertToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .budget(category.getBudget())
                .type(category.getType())
                .build();
    }

    private Category convertToEntity(CategoryDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .budget(dto.getBudget())
                .type(dto.getType())
                .build();
    }
}
