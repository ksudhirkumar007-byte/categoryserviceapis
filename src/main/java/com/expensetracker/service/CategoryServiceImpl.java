package com.expensetracker.service;

import com.expensetracker.client.ExpenseClient;
import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.dto.MonthSummaryDTO;
import com.expensetracker.model.Category;
import com.expensetracker.model.MonthSummary;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.MonthSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements  CategoryService {

    private final CategoryRepository categoryRepository;

    private final MonthSummaryRepository monthSummaryRepository;

    private final ExpenseClient expenseClient;

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
    public void summariseMonthAndUpdateCurrentMonth(String month) {
        List<Category> allCategories = categoryRepository.findAll();
        List<ExpenseDTO> expensesOfPrevMonth = expenseClient.getAllExpensesOfThisMonth(month);
        System.out.println("expenses of prev month "+month+" are "+expensesOfPrevMonth.size());
        for(Category category:allCategories){
            System.out.println("category Id is "+category.getId());
            MonthSummaryDTO monthSummaryDTO = new MonthSummaryDTO();
            monthSummaryDTO.setCategoryName(category.getName());
            monthSummaryDTO.setType(category.getType());
            monthSummaryDTO.setBudget(category.getBudget());
            double totalSpent = expensesOfPrevMonth.stream().filter(e -> e.getCategory_id().equals(category.getId())).mapToDouble(ExpenseDTO::getAmount).sum();
            System.out.println(totalSpent+" amount spent for "+category.getName());
            monthSummaryDTO.setTotalSpent(totalSpent);
            System.out.println("month is "+month);
            monthSummaryDTO.setMonth(month);
            MonthSummary monthSummary = convertToSummaryEntity(monthSummaryDTO);
            System.out.println("month summary totalSpent is "+monthSummary.getTotalSpent());
            monthSummaryRepository.save(monthSummary);
        }
       // categoryRepository.resetTotalsAndSetNewMonth(getNextMonth(month));
    }

    @Override
    public List<MonthSummaryDTO> summarisedCategoriesPerMonth(String month) {
        List<MonthSummaryDTO> byMonth = monthSummaryRepository.findByMonth(month).stream().map(this::convertToSummaryDTO).collect(Collectors.toList());
        System.out.println("month summaries list "+byMonth.size());
        return byMonth;
    }

    @Override
    public List<MonthSummaryDTO> summarisedCategoriesPerCategory(String category) {
        List<MonthSummaryDTO> byCategory = monthSummaryRepository.findByCategoryName(category).stream().map(this::convertToSummaryDTO).collect(Collectors.toList());
        return byCategory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthSummaryDTO> getAllMonthSummaries() {
        return monthSummaryRepository.findAll().stream()
                .map(this::convertToSummaryDTO)
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

    private MonthSummaryDTO convertToSummaryDTO(MonthSummary summary) {
        return MonthSummaryDTO.builder()
                .id(summary.getId())
                .categoryName(summary.getCategoryName())
                .budget(summary.getBudget())
                .type(summary.getType())
                .totalSpent(summary.getTotalSpent())
                .month(summary.getMonth())
                .build();
    }

    private MonthSummary convertToSummaryEntity(MonthSummaryDTO dto) {
        return MonthSummary.builder()
                .categoryName(dto.getCategoryName())
                .budget(dto.getBudget())
                .type(dto.getType())
                .totalSpent(dto.getTotalSpent())
                .month(dto.getMonth())
                .build();
    }
    public static String getNextMonth(String input) {
        // Define the input and output formats
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy");

        // Parse the input date (assumes day = 1)
        LocalDate date = LocalDate.parse(input, formatter).withDayOfMonth(1);

        // Add 1 month
        LocalDate nextMonth = date.plusMonths(1);

        // Return in same format
        return nextMonth.format(formatter);
    }
}
