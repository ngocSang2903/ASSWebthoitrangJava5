package com.code.AssJava5.service;

import com.code.AssJava5.entity.Category;
import com.code.AssJava5.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);

    void deleteById(Integer id);

    List<Product> findAll();

    Optional<Product> findById(Integer id);

    Page<Product> findAll(Integer page, Integer limit);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByNameLike(String name, Pageable pageable);

    Page<Product> findByField(Integer page, Integer limit,String field, String name);

    Page<Product> findByPrice(double min, double max, Pageable pageable);
}
