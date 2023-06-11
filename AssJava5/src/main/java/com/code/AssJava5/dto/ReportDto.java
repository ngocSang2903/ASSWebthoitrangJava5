package com.code.AssJava5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportDto {
    private String categoryName;
    private Double totalPrice;
    private Long productCount;

    public ReportDto(String categoryName, Double totalPrice, Long productCount) {
        this.categoryName = categoryName;
        this.totalPrice = totalPrice;
        this.productCount = productCount;
    }

    // getters and setters
}