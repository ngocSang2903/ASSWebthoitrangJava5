package com.code.AssJava5.dto;

import com.code.AssJava5.entity.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Không được để trống!")
    @Length(min = 5, max = 50, message = "Phải có từ 5 - 50  ký tự!")
    private String name;

    @NotNull(message = "Không được để trống!")
    private Double price;

    @NotEmpty(message = "Không được để trống!")
    private String description;

//    @NotNull(message = "Không được để trống!")
    private MultipartFile img;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate = new Date();

    private Boolean available;

    // bi-directional many-to-one association to Category
    private Category category;
}
