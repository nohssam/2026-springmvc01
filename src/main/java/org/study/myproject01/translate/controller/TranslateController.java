package org.study.myproject01.translate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.study.myproject01.translate.service.GoogleTranslateService;

@Slf4j
@Controller
public class TranslateController {
    @Autowired
    private GoogleTranslateService googleTranslateService;

    @GetMapping("/translate")
    public String translateForm() {
        return "translate/inputForm";
    }

    @PostMapping("/translateOk")
    public String translateOk(
            @RequestParam("text") String text,
            @RequestParam(value = "source", required = false) String source,
            Model model) {
        // target은 항상 한국어(ko) 고정
        String target = "ko";
        try {
            String translated = googleTranslateService.getTranslate(text, source, target);

            // log.info("translated : {} " , translated);

            model.addAttribute("original", text);
            model.addAttribute("translated", translated);
            model.addAttribute("source", source);
            model.addAttribute("target", target);
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
        }
        return "translate/result";
    }
}
