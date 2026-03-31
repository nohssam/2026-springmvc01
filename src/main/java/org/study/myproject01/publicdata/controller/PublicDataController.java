package org.study.myproject01.publicdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicDataController {
    @GetMapping("/weather")
    public String weather(){ return "weatherJson";}

    @GetMapping("/weatherJsonForm")
    public String weatherJSON(){ return "publicdata/weatherJsonForm";}

    @GetMapping("/weatherXmlForm")
    public String weatherXmlForm(){ return "publicdata/weatherXmlForm";}

    @GetMapping("/culturalEventInfo")
    public String culturalEventInfo(){ return "publicdata/culturalForm";}
}
