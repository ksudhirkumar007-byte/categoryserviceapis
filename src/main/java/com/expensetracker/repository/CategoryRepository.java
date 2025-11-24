package com.expensetracker.repository;

import com.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Modifying
    @Query("UPDATE Category c SET c.totalSpent = 0, c.currentMonth = :newMonth")
    void resetTotalsAndSetNewMonth(@Param("newMonth") String newMonth);
}