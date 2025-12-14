package com.expensetracker.repository;

import com.expensetracker.model.MonthSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthSummaryRepository extends JpaRepository<MonthSummary,Long> {
   List<MonthSummary> findByMonth(String month);
}
