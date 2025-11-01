package com.expensetracker.controller;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        allCategories.forEach(i-> System.out.println(i.getName()));
        return  allCategories;
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category) {
        CategoryDTO created = categoryService.createCategory(category);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}