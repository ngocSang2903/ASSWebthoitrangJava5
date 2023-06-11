package com.code.AssJava5.service.Impl;

import com.code.AssJava5.dto.CategoryDto;
import com.code.AssJava5.entity.Category;
import com.code.AssJava5.entity.Product;
import com.code.AssJava5.repositories.CategoryRepo;
import com.code.AssJava5.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public Category create(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category udpate(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void delete(Integer id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepo.findById(id);
    }

    @Override
    public Page<Category> findAll(Integer page, Integer limit) {
        Pageable pageable= PageRequest.of(page,limit);
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }
}
