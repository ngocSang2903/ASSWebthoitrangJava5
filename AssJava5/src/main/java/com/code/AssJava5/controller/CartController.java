package com.code.AssJava5.controller;

import com.code.AssJava5.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
     @Autowired
     CartService cartService;

     @GetMapping("/cart")
     public String CartProduct(Model model){
         model.addAttribute("cart",cartService.getItems());
         model.addAttribute("amout",cartService.getAmount());
         model.addAttribute("count",cartService.getCount());
         return "cart";
     }

     @GetMapping("/cart/remove/{id}")
     public String remove(@PathVariable("id") Integer id){
         cartService.remove(id);
         return "redirect:/cart";
     }

    @PostMapping("/cart/update/{id}")
    public String update(@PathVariable("id") Integer id,@RequestParam("qty") Integer qty){
        System.out.println(qty);
        cartService.update(id,qty);
        return "redirect:/cart";
    }

    @GetMapping("/cart/add/{id}")
    public String add(@PathVariable("id") Integer id){
         cartService.add(id);
         return "redirect:/cart";
    }
}
