package org.study.myproject01.publicdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicDataController {
    @GetMapping("/weather")
    public String weather(){ return "publicdata/weatherForm";}
}
