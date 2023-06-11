package com.code.AssJava5.controller;

import com.code.AssJava5.dto.AccountDto;
import com.code.AssJava5.entity.Account;
import com.code.AssJava5.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/create")
    public String create(Model model,@ModelAttribute("accountdto")AccountDto accountDto){
        model.addAttribute("action","/admin/account/save");
        return "Admin/Account";
    }

    @ModelAttribute("activated")
    public Map<Boolean,String> getActived(){
        Map<Boolean,String> map=new HashMap<>();
        map.put(true,"Yes");
        map.put(false,"No");
        return map;
    }

    @ModelAttribute("admin")
    public Map<Boolean,String> getadmin(){
        Map<Boolean,String> map=new HashMap<>();
        map.put(true,"Yes");
        map.put(false,"No");
        return map;
    }

    @PostMapping("/save")
    public String save(Model model,@Valid @ModelAttribute("accountdto")AccountDto accountDto){

        Account account=new Account(accountDto.getUsername(),accountDto.getPassword()
                ,accountDto.getFullname(),accountDto.getEmail(),null,
                accountDto.getActivated(),accountDto.getAdmin(),null);
        accountService.save(account);
        return "redirect:/admin/account/list";
    }

    @PostMapping("/udpate/{username}")
    public String save(Model model,@Valid @ModelAttribute("accountdto")AccountDto accountDto,@PathVariable("username") String username){

        Account account=new Account(accountDto.getUsername(),accountDto.getPassword()
                ,accountDto.getFullname(),accountDto.getEmail(),null,
                accountDto.getActivated(),accountDto.getAdmin(),null);
        accountService.save(account);
        return "redirect:/admin/account/list";
    }

    @GetMapping("/edit/{username}")
    public String editAccount(Model model,@PathVariable("username") String username){
        Optional<Account> account=accountService.findById(username);
        model.addAttribute("accountdto",account);
        model.addAttribute("action","/admin/account/udpate/"+account.get().getUsername());
        return "Admin/Account";
    }

    @GetMapping("/delete/{username}")
    public String deleteAccount(Model model,@PathVariable("username") String username){
        accountService.delete(username);
        return "redirect:/admin/account/list";
    }

    @GetMapping("/list")
    public String Accountview(@ModelAttribute("accountdto") AccountDto accountDto, ModelMap modelMap,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "sort", defaultValue = "username,asc") String sortField) {
        int pageSize = 5;
        String[] sortParams = sortField.split(",");
        String sortFieldName = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(sortDirection, sortFieldName);

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Account> accounts = accountService.findAll(pageable);
        System.out.println(accounts);

        modelMap.addAttribute("sortField", sortFieldName);
        modelMap.addAttribute("sortDirection", sortDirection);
        modelMap.addAttribute("items", accounts);
        return "Admin/AccountView";
    }
}
