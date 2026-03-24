package org.study.myproject01.members.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.study.myproject01.members.service.MemberService;
import org.study.myproject01.members.vo.MemberVO;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/loginForm")
    public String loginForm() {return "members/loginForm";}

    @PostMapping("/loginok")
    public String loginOk(MemberVO memberVO, HttpSession session, Model model) {

        // 회원검사
        MemberVO mvo = memberService.getMemberDetail(memberVO);
        if(mvo==null){
            // 회원정보 없음
            model.addAttribute("logInChk","fail");
            return "members/loginForm";
        }else{
            // 회원정보 있음
            // 비밀번호가 검증
            if(passwordEncoder.matches(memberVO.getM_pw(), mvo.getM_pw())){
                // 로그인 성공
                session.setAttribute("logInChk","ok");
                // 보안상 위험 하다.(원래는 id나 name, 등 제일 최소만 담는다.)
                session.setAttribute("mvo",mvo);
                return "redirect:/shop/list";
            }else{
                // 아이디는 있지만 비번이 틀림
                model.addAttribute("logInChk","fail");
                return "members/loginForm";
            }
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 하나씩 삭제 할때
        // session.removeAttribute("logInChk");

        // 세션 초기화
        session.invalidate();
        return "redirect:/shop/list";
    }
    @GetMapping("/joinForm")
    public String joinForm() {
        return "members/joinForm";
    }
}
