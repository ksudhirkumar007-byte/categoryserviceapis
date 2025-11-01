package com.expensetracker.service;

import com.expensetracker.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
}
