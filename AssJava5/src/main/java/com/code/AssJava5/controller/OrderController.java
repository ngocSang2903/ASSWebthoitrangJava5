package com.code.AssJava5.controller;

import com.code.AssJava5.entity.Category;
import com.code.AssJava5.entity.Order;
import com.code.AssJava5.entity.OrderDetail;
import com.code.AssJava5.repositories.OrderDetailRepo;
import com.code.AssJava5.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderRepo order;

    @Autowired
    OrderDetailRepo orderDetailRepo;

    @GetMapping("/list")
    public String getOrder(Model model,@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "sort", defaultValue = "id,asc") String sortField){

        int pageSize = 5; // Number of items per page
        String[] sortParams = sortField.split(",");
        String sortFieldName = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.ASC;

        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(sortDirection, sortFieldName);

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Order> order1 = order.findAll(pageable);

        model.addAttribute("sortField", sortFieldName);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("items", order1);
        return "Admin/Order";
    }

    @GetMapping("/listdetail/{orderid}")
    public String getOrderDetail(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "sort", defaultValue = "price,asc") String sortField,
                                 @PathVariable("orderid") Integer id){

        int pageSize = 5; // Number of items per page
        String[] sortParams = sortField.split(",");
        String sortFieldName = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.ASC;

        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(sortDirection, sortFieldName);

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<OrderDetail> order1 = orderDetailRepo.getAll(id,pageable);

        model.addAttribute("sortField", sortFieldName);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("items", order1);
        model.addAttribute("countsum", orderDetailRepo.countSumOrder());
        return "Admin/OrderDtail";
    }
}
