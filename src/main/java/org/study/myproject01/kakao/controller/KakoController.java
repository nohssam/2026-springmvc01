package org.study.myproject01.kakao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.myproject01.kakao.vo.AddrVO;

@Controller
@RequestMapping("/daum")
public class KakoController {
    @GetMapping("/addrForm")
    public String addrForm() {
        return "kakao/addrForm";
    }
    @PostMapping("/addrresult")
    public String addrResult(AddrVO addrVO, Model model) {
        // 원래는 DB 처리
        model.addAttribute("addrVO", addrVO);
        return "kakao/addrResult";
    }
    @GetMapping("/kakaomapindex")
    public String kakaomapindex() {
        return "kakao/mapindex";
    }
    @GetMapping("/map01")
    public String map01() {
        return "kakao/kakao_map01";
    }
    @GetMapping("/map02")
    public String map02() {
        return "kakao/kakao_map02";
    }
    @GetMapping("/map03")
    public String map03() {
        return "kakao/kakao_map03";
    }
    @GetMapping("/map04")
    public String map04() {
        return "kakao/kakao_map04";
    }
}
