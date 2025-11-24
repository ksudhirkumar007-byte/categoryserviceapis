package com.expensetracker.repository;

import com.expensetracker.model.MonthSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthSummaryRepository extends JpaRepository<MonthSummary,Long> {
}
