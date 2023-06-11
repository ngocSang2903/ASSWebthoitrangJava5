package com.code.AssJava5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReportDateDto {

    private Date createDate;
    private Double totalPrice;
    private Long productCount;

    public ReportDateDto(Date createDate, Double totalPrice, Long productCount) {
        this.createDate = createDate;
        this.totalPrice = totalPrice;
        this.productCount = productCount;
    }
}
