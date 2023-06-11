package com.code.AssJava5.service.Impl;

import com.code.AssJava5.entity.Category;
import com.code.AssJava5.entity.Product;
import com.code.AssJava5.repositories.ProductRepo;
import com.code.AssJava5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id);
    }

    @Override
    public Page<Product> findAll(Integer page, Integer limit) {
        Pageable pageable= PageRequest.of(page,limit);
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<Product> findAllByNameLike(String name, Pageable pageable) {
        return productRepo.findAllByNameLike(name,pageable);
    }

    @Override
    public Page<Product> findByPrice(double min, double max, Pageable pageable) {
        return productRepo.findByPrice(min,max,pageable);
    }

    Boolean count = true;

    @Override
    public Page<Product> findByField(Integer page, Integer limit, String field, String name) {
        if(name.isEmpty()){
            Pageable pageable=PageRequest.of(page,limit, Sort.by(Sort.Direction.ASC,"id"));
            return productRepo.findAllByNameLike(name,pageable);
        }else if(field.equals("")){
            Pageable pageable=PageRequest.of(page,limit);
            return productRepo.findAll(pageable);
        }else{
            if(count){
                count=false;
                Pageable pageable=PageRequest.of(page,limit, Sort.by(Sort.Direction.ASC,"id"));
                return productRepo.findAll(pageable);
            }else{
                count=true;
                Pageable pageable=PageRequest.of(page,limit, Sort.by(Sort.Direction.DESC,"id"));
                return productRepo.findAll(pageable);
            }
        }
    }
}
