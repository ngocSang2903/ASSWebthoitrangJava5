package com.code.AssJava5.controller;

import com.code.AssJava5.dto.MailInfo;
import com.code.AssJava5.service.CartService;
import com.code.AssJava5.service.MailerService;
import com.code.AssJava5.service.SessionService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {
    @Autowired
    CartService cartService;

    @Autowired
    SessionService sessionService;

    @Autowired
    MailerService mailerService;

    @GetMapping("/admin")
    public String admin(){
//        MailInfo mailInfo= ;
//        mailerService.queue(new MailInfo("sangnnph28386@fpt.edu.vn","xin chào","fd"));
        return "admin";
    }

    @PostMapping("/admin")
    public String postemail(@RequestParam("email") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("body") String body) throws MessagingException {
        mailerService.send(new MailInfo(to,subject,body));
        System.out.println("success");
        return "admin";
    }



}
