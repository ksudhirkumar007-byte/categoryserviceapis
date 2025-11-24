package com.expensetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthSummaryDTO {
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private Double budget;

    @NotBlank(message = "name is required")
    private String categoryName;

    @NotBlank(message = "type of expense")
    private String type; // values: "fixed" or "variable"

    private Double totalSpent;

    private String month;
}
