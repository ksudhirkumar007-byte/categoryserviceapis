package com.expensetracker.service;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.MonthSummaryDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    List<MonthSummaryDTO> getAllMonthSummaries();
    boolean deleteCategory(Long id);

    CategoryDTO updateCategory(Long id,CategoryDTO category);

    void summariseMonthAndUpdateCurrentMonth(@Valid String month);
    List<MonthSummaryDTO> summarisedCategoriesPerMonth(@Valid String month);

}
