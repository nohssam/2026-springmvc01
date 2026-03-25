package org.study.myproject01.sns.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.study.myproject01.sns.service.KakoLoginUtil;
import org.study.myproject01.sns.vo.KakaoUserVO;

@Slf4j
@Controller
public class SnsController {

    @GetMapping("/kakaologin")
    public String kakaologin(HttpServletRequest request, HttpSession session) {
        try{
            // 인증코드 요청이 성공하면 request에 정보가 있다.
            String code = request.getParameter("code");
            // log.info("요청 코드 : {} ", code);

            // 인가코드를 가지고 AccessToken 요청하는 메서드 호출
            String accessToken =  KakoLoginUtil.getAccessToken(code);
            // log.info("accessToken:{}",accessToken);

            // accessToken 을 가지고 사용자 정보 요청 하는 메서드 호출
            // 결과는 vo형태로
            KakaoUserVO kakaoUserVO =  KakoLoginUtil.getUserInfo(accessToken);
            log.info("name:{}",kakaoUserVO.getProperties().getNickname());
            log.info("name:{}",kakaoUserVO.getKakao_account().getName());
            log.info("name:{}",kakaoUserVO.getKakao_account().getProfile().getNickname());

            log.info("phone:{}",kakaoUserVO.getKakao_account().getPhone_number());
            log.info("email:{}",kakaoUserVO.getKakao_account().getEmail());

            // 전화번호나 email 로 기존 회원 여부 확인
            // 전화번호로 기존 회원 조회
            String mobile = kakaoUserVO.getKakao_account().getPhone_number();
            String phone = mobile.replaceAll("\\+82\\s?", "0").trim();


            return "redirect:/shop/list";
        } catch (Exception e) {
            log.info("kakaologin error : {}", e.getMessage());
            return "members/loginForm";
        }
    }
}
