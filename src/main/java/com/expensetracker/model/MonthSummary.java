package com.expensetracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MonthSummaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Column(nullable = false)
    private Double budget;

    @Column(nullable = false)
    private String type;


    private Double totalSpent;

    @Column(nullable = false)
    private String month;

    // Getters and Setters
}