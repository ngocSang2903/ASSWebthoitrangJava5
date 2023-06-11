package com.code.AssJava5.controller;

import com.code.AssJava5.dto.AccountDto;
import com.code.AssJava5.dto.ChangePassDto;
import com.code.AssJava5.dto.MailInfo;
import com.code.AssJava5.entity.Account;
import com.code.AssJava5.service.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    AccountService accountService;
    @Autowired
    CookieService cookieService;
    @Autowired
    SessionService sessionService;
    @Autowired
    CartService cartService;
    @Autowired
    MailerService mailerService;

    @GetMapping("/login")
    public String Login(Model model)
    {
        model.addAttribute("count",cartService.getCount());
        sessionService.remove("error");
        return "Login.html";
    }

    @PostMapping("/login")
    public String PostLogin(@Valid @ModelAttribute Account acc,
                            @RequestParam("username") String user,
                            @RequestParam("password") String pass,
                            @RequestParam(name = "remember",defaultValue = "false") Boolean rm, RedirectAttributes redirectAttributes
    ) throws NoSuchAlgorithmException
    {
        Account account=accountService.findByUser(user);
        if(account==null){
            redirectAttributes.addFlashAttribute("error","Incorrect account or password");
            return "redirect:/login";
        }
        if(user.equals(account.getUsername()) && pass.equals(account.getPassword()))
        {
            sessionService.set("user",account);
            sessionService.set("isLogin",true);
            if(rm==true)
            {
                cookieService.add("username",user,10);
                cookieService.add("password",accountService.setHashMD5(pass),10);
            }else{
                cookieService.remove("username");
                cookieService.remove("password");
            }
            if(account.isAdmin()==true)
            {
                sessionService.set("isAdmin",true);
                return "redirect:/admin";
            }else {
                sessionService.set("isAdmin",false);
            }
            return "redirect:/index";
        }else {
            sessionService.set("isLogin",false);
            sessionService.remove("isLogin");
            redirectAttributes.addFlashAttribute("error","Incorrect account or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model){
        model.addAttribute("count",cartService.getCount());
        sessionService.remove("user");
        sessionService.remove("isLogin");
        sessionService.remove("isAdmin");
        cookieService.remove("username");
        cookieService.remove("password");
        return "redirect:/index";
    }

    @GetMapping("/forgotPassWord")
    public String forgotPass(){

        return "forgotPassword.html";
    }

    @PostMapping("/forgotPassWord")
    public String SendMailPass(Model model,@RequestParam("email") String email) throws MessagingException {

        Account account=accountService.findByEmail(email);

        if(account==null){
            model.addAttribute("Messges","Email khong ton tai !");
            return "forgotPassword.html";
        }else {
            String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // chuỗi các ký tự được phép
            int length = 10; // độ dài của chuỗi ký tự

            Random random = new Random();
            StringBuilder sb = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(allowedChars.length());
                char randomChar = allowedChars.charAt(randomIndex);
                sb.append(randomChar);
            }

            String randomString = sb.toString();
            System.out.println(randomString);

            account.setPassword(randomString);
            accountService.save(account);

            String subject="Yêu cầu lấy lại mật khẩu ";
            String body="Thông tin bảo mật, mật khẩu lấy lại của bạn là :"+randomString;

            mailerService.send(new MailInfo(email,subject,body));
            model.addAttribute("Messges","Vui long check mail de lay pass !");
        }

        return "forgotPassword.html";
    }


    @GetMapping("/register")
    public String register(@ModelAttribute("register") AccountDto account){
        return "register.html";
    }

    @PostMapping("/register")
    public String saveregister(Model model, @Validated @ModelAttribute("register") AccountDto account) throws MessagingException {

        Account accountByEmail= accountService.findByEmail(account.getEmail());
        Account accountByUser= accountService.findByUser(account.getUsername());

        if(accountByUser !=null ){
            model.addAttribute("Messges","Username da ton tai !");
            return "register.html";
        }else if(accountByEmail !=null ){
            model.addAttribute("Messges","Email da ton tai !");
            return "register.html";
        } else {

            String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // chuỗi các ký tự được phép
            int length = 10; // độ dài của chuỗi ký tự

            Random random = new Random();
            StringBuilder sb = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(allowedChars.length());
                char randomChar = allowedChars.charAt(randomIndex);
                sb.append(randomChar);
            }

            String randomString = sb.toString();
            System.out.println(randomString);

            account.setPassword(randomString);
            account.setActivated(true);
            account.setImg(null);
            account.setAdmin(false);

            Account account1=new Account(account.getUsername(),account.getPassword(),account.getFullname(),account.getEmail(),null,account.getActivated(),account.getAdmin(),null);

            accountService.save(account1);

            String subject="Tạo tài khoản thành công ";
            String body="Thông tin bảo mật, tài khoản và mật khẩu lần lượt là : "+account.getUsername()+" , "+ randomString;

            mailerService.send(new MailInfo(account.getEmail(),subject,body));

            model.addAttribute("Messges","Tao tai khoan thanh con vui long kiem tra mail!");
        }
        return "register.html";
    }

    @GetMapping("/changepass")
    public String changepass(@ModelAttribute("changepass")ChangePassDto changePassDto){
        return "ChangePass";
    }


    @PostMapping("/changepass")
    public String postchangepass(@ModelAttribute("changepass") ChangePassDto changePassDto, Model model) {
        Account user = (Account) sessionService.get("user");

        if (user == null) {
            model.addAttribute("Messges", "Phải đăng nhập để thay đổi mật khẩu!");
            return "ChangePass";
        }

        if (!changePassDto.getPassword().equals(user.getPassword())) {
            System.out.println(user.getPassword());
            System.out.println(changePassDto.getPassword());
            model.addAttribute("Messges", "Mật khẩu không chính xác!");
            return "ChangePass";
        } else if (!changePassDto.getPasswordnew().equals(changePassDto.getConfirmpassword())) {
            model.addAttribute("Messges", "Mật khẩu không trùng khớp!");
            return "ChangePass";
        } else {
            user.setPassword(changePassDto.getConfirmpassword());
            accountService.save(user);
            model.addAttribute("Messges", "Thay đổi mật khẩu thành công!");
        }

        return "ChangePass";
    }

}
