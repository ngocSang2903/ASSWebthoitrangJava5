package com.code.AssJava5.repositories;

import com.code.AssJava5.dto.ReportDateDto;
import com.code.AssJava5.dto.ReportDto;
import com.code.AssJava5.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    Page<Product> findByPrice(double min, double max, Pageable pageable);

    Page<Product> findAllByNameLike(String name, Pageable pageable);

    @Query("SELECT new com.code.AssJava5.dto.ReportDto(c1_0.name, SUM(p1_0.price), COUNT(p1_0.id)) FROM Product p1_0 JOIN p1_0.category c1_0 GROUP BY c1_0.name ORDER BY SUM(p1_0.price) DESC")
    Page<ReportDto> getInventoryByCategory(Pageable pageable);

    @Query("SELECT new  com.code.AssJava5.dto.ReportDateDto(p.createDate, SUM(p.price), COUNT(p.id)) FROM Product p GROUP BY p.createDate ORDER BY SUM(p.price) DESC")
    Page<ReportDateDto> getInventoryByDate(Pageable pageable);

}
