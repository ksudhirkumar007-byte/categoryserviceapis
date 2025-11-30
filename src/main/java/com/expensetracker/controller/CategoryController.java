package com.expensetracker.controller;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.model.Category;
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
@CrossOrigin(origins = "https://expensetrackerupdatedui.onrender.com")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/keepalive")
    public String ping() {
        System.out.println("keeping services alive");
        return "alive";
    }

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

    @DeleteMapping("/{id}")
    public boolean deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO category){
      CategoryDTO updatedCategory =categoryService.updateCategory(id,category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
    }

    @PostMapping("/summarise-categories")
    public boolean summariseMonthAndUpdateCategories(@Valid @RequestBody String month){
        try{
            categoryService.summariseMonthAndUpdateCurrentMonth(month);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}