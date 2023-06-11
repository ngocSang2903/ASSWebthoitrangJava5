package com.code.AssJava5.controller;

import com.code.AssJava5.dto.ReportDateDto;
import com.code.AssJava5.dto.ReportDto;
import com.code.AssJava5.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/list")
    public String report(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "page1", defaultValue = "0") int page1){

       int pageSize=5;
        Pageable pageable = PageRequest.of(page, pageSize);
        Pageable pageable1 = PageRequest.of(page1, pageSize);
        Page<ReportDto> order1 = productRepo.getInventoryByCategory(pageable);

        Page<ReportDateDto> reportDateDtos = productRepo.getInventoryByDate(pageable1);

        model.addAttribute("items", order1);
        model.addAttribute("items1", reportDateDtos);
        return "Admin/Report";
    }


}
