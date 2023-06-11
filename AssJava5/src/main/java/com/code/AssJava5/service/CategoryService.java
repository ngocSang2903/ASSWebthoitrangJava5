package com.code.AssJava5.service;

import com.code.AssJava5.dto.CategoryDto;
import com.code.AssJava5.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category create(Category category);

    Category udpate(Category category);

    void delete(Integer id);

    List<Category> findAll();

    Optional<Category> findById(Integer id);

    Page<Category> findAll(Integer page, Integer limit);

    Page<Category> findAll(Pageable pageable);
}
