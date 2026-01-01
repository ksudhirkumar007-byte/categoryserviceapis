package com.expensetracker.client;


import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.ExpenseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "expense-service", url = "http://xpenss.in")  // Use real URL or Discovery service name
public interface ExpenseClient {

    @GetMapping("/api/expenses/month/{month}")
    List<ExpenseDTO> getAllExpenses(@PathVariable("month") String month);

    @GetMapping("/api/expenses")
    List<ExpenseDTO> getAllExpenses();
}
