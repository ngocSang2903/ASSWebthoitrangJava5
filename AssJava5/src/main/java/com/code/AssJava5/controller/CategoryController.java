package com.code.AssJava5.controller;

import com.code.AssJava5.dto.CategoryDto;
import com.code.AssJava5.entity.Category;
import com.code.AssJava5.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public String category(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "sort", defaultValue = "name,asc") String sortField) {
        int pageSize = 5; // Number of items per page
        String[] sortParams = sortField.split(",");
        String sortFieldName = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.ASC;

        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(sortDirection, sortFieldName);

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Category> categoryPage = categoryService.findAll(pageable);

        model.addAttribute("sortField", sortFieldName);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("items", categoryPage);

        return "Admin/categoryView";
    }
    @GetMapping("/create")
    public String viewinsertcategory(Model model){
        CategoryDto categoryDto=new CategoryDto();
        model.addAttribute("action","/admin/category/save");
        model.addAttribute("categorydto",categoryDto);
        return "Admin/category";
    }

    @PostMapping("/save")
    public String addCategory(@Validated @ModelAttribute("categorydto")CategoryDto categoryDto){

        Category category=new Category(categoryDto.getId(),categoryDto.getName(),null);
        categoryService.create(category);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Category categories=categoryService.findById(id).get();
        model.addAttribute("categorydto",categories);
        model.addAttribute("action","/admin/category/update/"+categories.getId());
        return "Admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, ModelMap modelMap){
        categoryService.delete(id);
        return "redirect:/admin/category/list";
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,
                    @Validated @ModelAttribute("categorydto")CategoryDto categoryDto){
        Category category=new Category(categoryDto.getId(),categoryDto.getName(),null);
        categoryService.create(category);
        return "redirect:/admin/category/list";
    }

}
