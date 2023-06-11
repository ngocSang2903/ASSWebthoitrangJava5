package com.code.AssJava5.controller;

import com.code.AssJava5.entity.Account;
import com.code.AssJava5.service.CartService;
import com.code.AssJava5.service.SessionService;
import org.springframework.ui.Model;
import com.code.AssJava5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    SessionService sessionService;

    @GetMapping("/index")
    public String home(Model model,@RequestParam(name = "page") Optional<Integer> page){
        model.addAttribute("count",cartService.getCount());
        model.addAttribute("items",productService.findAll(page.orElse(0),8));
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model, @RequestParam(name = "page") Optional<Integer> page,
                       @RequestParam(name="field",defaultValue = "")String field){
        model.addAttribute("count",cartService.getCount());
        if(field.equals("")){
            model.addAttribute("items",productService.findAll(page.orElse(0),9));
        }else{
            model.addAttribute("items",productService.findByField(page.orElse(0),9,field,""));
        }
        return "shop";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        model.addAttribute("items",productService.findById(id).get());
        model.addAttribute("count",cartService.getCount());
        return "detail";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Account account=sessionService.get("user");
        model.addAttribute("username",account.getUsername());
        model.addAttribute("email",account.getEmail());
        model.addAttribute("fullname",account.getFullname());
        return "profile";
    }

    @GetMapping("/detail")
    public String detail(Model model){
        model.addAttribute("count",cartService.getCount());
        return "detail";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        model.addAttribute("count",cartService.getCount());
        return "contact";
    }

}
