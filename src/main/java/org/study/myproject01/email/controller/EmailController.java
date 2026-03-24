package org.study.myproject01.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
public class EmailController {
    @GetMapping("/emailForm")
    public String emailForm(){
        return "email/emailForm";
    }
}
