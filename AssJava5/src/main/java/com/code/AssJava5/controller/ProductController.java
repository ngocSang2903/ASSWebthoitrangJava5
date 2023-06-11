package com.code.AssJava5.controller;

import com.code.AssJava5.dto.ProductDto;
import com.code.AssJava5.entity.Category;
import com.code.AssJava5.entity.Product;
import com.code.AssJava5.service.CategoryService;
import com.code.AssJava5.service.ProductService;
import jakarta.servlet.ServletContext;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private ServletContext app;

    @GetMapping("create")
    public String viewCreate(@ModelAttribute("productdto") ProductDto productDto, ModelMap modelMap) {
        modelMap.addAttribute("productdto", productDto);
        modelMap.addAttribute("action","/admin/product/save");
        return "Admin/Product";
    }

    @ModelAttribute("categorys")
    public Map<Integer, String> getCategory() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < categoryService.findAll().size(); i++) {
            map.put(categoryService.findAll().get(i).getId(), categoryService.findAll().get(i).getName());
        }
        return map;
    }

    @ModelAttribute("availables")
    public Map<Boolean, String> getAvailable() {
        Map<Boolean, String> map = new HashMap<>();
        map.put(true, "Yes");
        map.put(false, "No");
        return map;
    }

    @GetMapping("/list")
    public String productview(@ModelAttribute("productdto") ProductDto productDto, ModelMap modelMap,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "sort", defaultValue = "name,asc") String sortField) {
        int pageSize = 5;
        String[] sortParams = sortField.split(",");
        String sortFieldName = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(sortDirection, sortFieldName);

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Product> productPage = productService.findAll(pageable);

        modelMap.addAttribute("sortField", sortFieldName);
        modelMap.addAttribute("sortDirection", sortDirection);
        modelMap.addAttribute("items", productPage);
        return "Admin/ProductView";
    }

    @PostMapping ("/search/price")
    public String findByPrice(@RequestParam("min") Optional<Double> min,ModelMap modelMap,
                              @RequestParam("max") Optional<Double> max,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "sort", defaultValue = "name,asc") String sortField){

        int pageSize=5;
        String[] sortParams = sortField.split(",");
        String sortname=sortParams[0];
        Sort.Direction sortDirection=Sort.Direction.ASC;
        if(sortParams[1].equals("desc")){
            sortDirection=Sort.Direction.DESC;
        }
        Sort sort=Sort.by(sortDirection,sortname);
        Pageable pageable=PageRequest.of(page,pageSize,sort);

        double minPrice=min.orElse(Double.MIN_VALUE);
        double maxPrice=max.orElse(Double.MAX_VALUE);

        Page<Product> items=productService.findByPrice(minPrice,maxPrice,pageable);

        modelMap.addAttribute("sortField", sortname);
        modelMap.addAttribute("sortDirection", sortDirection);
        modelMap.addAttribute("items", items);
        return "Admin/ProductView";
    }

    @GetMapping("/edit/{id}")
    public String editproduct(@PathVariable("id") Integer id, ModelMap modelMap) {
        Product product=productService.findById(id).get();
        modelMap.addAttribute("productdto", product);
        modelMap.addAttribute("action","/admin/product/update/"+product.getId());
        return "Admin/Product";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, ModelMap modelMap) {
        productService.deleteById(id);
        return "redirect:/admin/product/list";
    }

    @PostMapping("save")
    public String save(@Validated @ModelAttribute("productdto") ProductDto productDto,
                       @RequestPart("img") MultipartFile img) throws IOException {
        if (!img.isEmpty()) {
            String fileOrigionalName = img.getOriginalFilename();
            System.out.println(fileOrigionalName);
            //Date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyymmddhhmmss");
            LocalDateTime now = LocalDateTime.now();
            String datenow = dtf.format(now).toLowerCase();

            File file = new File("E:\\JAVA5\\AssJava5\\src\\main\\resources\\static\\img"+ datenow + "_" + fileOrigionalName);
            System.out.println(file.getAbsolutePath());
            img.transferTo(file);
            String absolutePath = datenow + "_" + fileOrigionalName;

            System.out.println(absolutePath);

            Product product=new Product(productDto.getId(),productDto.getName(),absolutePath,
                productDto.getDescription(),Double.parseDouble(String.valueOf(productDto.getPrice())),productDto.getCreateDate(),
                productDto.getAvailable(),productDto.getCategory(),null);
        productService.save(product);

        }
        return "redirect:/admin/product/list";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer productId,
                         @Validated @ModelAttribute("productdto") ProductDto productDto,
                         @RequestPart("img") MultipartFile img) throws IOException {

        Optional<Product> productOptional = productService.findById(productId); // Lấy thông tin sản phẩm hiện tại từ cơ sở dữ liệu

        productOptional.ifPresent(product -> {
            // Cập nhật thông tin sản phẩm từ form
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(Double.parseDouble(String.valueOf(productDto.getPrice())));
            product.setAvailable(productDto.getAvailable());
            product.setCategory(productDto.getCategory());

            if (!img.isEmpty()) {
                // Xử lý hình ảnh mới
                String fileOrigionalName = img.getOriginalFilename();
                //Date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyymmddhhmmss");
                LocalDateTime now = LocalDateTime.now();
                String datenow = dtf.format(now).toLowerCase();
                File file = new File("E:\\JAVA5\\AssJava5\\src\\main\\resources\\static\\img\\" + datenow + "_" + fileOrigionalName);
                try {
                    img.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String absolutePath = datenow + "_" + fileOrigionalName;
                product.setImg(absolutePath);
            }

            productService.save(product); // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
        });

        return "redirect:/admin/product/list";
    }



}
