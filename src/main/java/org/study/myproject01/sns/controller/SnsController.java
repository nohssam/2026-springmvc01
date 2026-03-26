package org.study.myproject01.sns.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.study.myproject01.members.service.MemberService;
import org.study.myproject01.members.vo.MemberVO;
import org.study.myproject01.sns.service.KakoLoginUtil;
import org.study.myproject01.sns.service.NaverUtil;
import org.study.myproject01.sns.vo.KakaoUserVO;
import org.study.myproject01.sns.vo.NaverUserVO;

@Slf4j
@Controller
public class SnsController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/kakaologin")
    public String kakaologin(HttpServletRequest request, HttpSession session) {
        try{
            // 인증코드 요청이 성공하면 request에 정보가 있다.
            String code = request.getParameter("code");
            // log.info("요청 코드 : {} ", code);

            // 인가코드를 가지고 AccessToken 요청하는 메서드 호출
            String accessToken =  KakoLoginUtil.getAccessToken(code);
            // log.info("accessToken:{}",accessToken);

            // 로그 아웃을 위해서 session에 accessToken 를 저장하자
            session.setAttribute("kakaoAccessToken", accessToken);

            // accessToken 을 가지고 사용자 정보 요청 하는 메서드 호출
            // 결과는 vo형태로
            KakaoUserVO kakaoUserVO =  KakoLoginUtil.getUserInfo(accessToken);
//            log.info("name:{}",kakaoUserVO.getProperties().getNickname());
//            log.info("name:{}",kakaoUserVO.getKakao_account().getName());
//            log.info("name:{}",kakaoUserVO.getKakao_account().getProfile().getNickname());
//
//            log.info("phone:{}",kakaoUserVO.getKakao_account().getPhone_number());
//            log.info("email:{}",kakaoUserVO.getKakao_account().getEmail());

            // 전화번호나 email 로 기존 회원 여부 확인
            // 전화번호로 기존 회원 조회
            String mobile = kakaoUserVO.getKakao_account().getPhone_number();
            String phone = mobile.replaceAll("\\+82\\s?", "0").trim();

            // 전화번호 기존 회원 조회 (이때 전화번호는 유일 해야 된다.)
            MemberVO memberVO = memberService.findByPhoneNumber(phone);
            if(memberVO != null){
                // 일반 기존회원이면 수정
                // kakao로 로그인 정보가 있으면 수정할 필요가 없다.(숙제)
                memberVO.setSns_provider("kakao");
                memberVO.setSns_email_kakao(kakaoUserVO.getKakao_account().getEmail());
                memberService.updateKakaoInfo(memberVO);

                // 일반로그인 처리를 참조
                // session.setAttribute("logInChk", "ok");
                // session.setAttribute("mvo", memberVO);

            }else{
               // 기존회원이 아니면 삽입
                memberVO = new MemberVO();
                memberVO.setM_id(kakaoUserVO.getId());
                memberVO.setM_name(kakaoUserVO.getKakao_account().getName());
                memberVO.setM_phone(phone);
                memberVO.setM_email(kakaoUserVO.getKakao_account().getEmail());
                memberVO.setSns_provider("kakao");
                memberVO.setSns_email_kakao(kakaoUserVO.getKakao_account().getEmail());
               memberService.getMembersSnsJoinOK(memberVO);
            }
            session.setAttribute("logInChk", "ok");
            session.setAttribute("mvo", memberVO);
            return "redirect:/shop/list";
        } catch (Exception e) {
            log.info("kakaologin error : {}", e.getMessage());
            return "members/loginForm";
        }
    }
    @GetMapping("/naverlogin")
    public String naverlogin(HttpServletRequest request, HttpSession session) {
        try{
            String code = request.getParameter("code");
            String state = request.getParameter("state");
            // log.info("code:{} state:{}", code, state);

            // 인가코드를 가지고 AccessToken 요청하는 메서드 호출
            String accessToken =  NaverUtil.getAccessToken(code, state);
           // log.info("accessToken : {}", accessToken);

            // 로그 아웃을 위해서 session에 accessToken 를 저장하자
            session.setAttribute("naverAccessToken", accessToken);

            // accessToken 을 가지고 사용자 정보 요청 하는 메서드 호출
            // 결과는 vo형태로
            NaverUserVO naverUserVO =  NaverUtil.getUserInfo(accessToken);
//            log.info("name : {}", naverUserVO.getResponse().getId());
//            log.info("name : {}", naverUserVO.getResponse().getNickname());
//            log.info("name : {}", naverUserVO.getResponse().getName());
//            log.info("name : {}", naverUserVO.getResponse().getEmail());
//            log.info("name : {}", naverUserVO.getResponse().getMobile());

            String phone = naverUserVO.getResponse().getMobile();
            // 전화번호 기존 회원 조회 (이때 전화번호는 유일 해야 된다.)
            MemberVO memberVO = memberService.findByPhoneNumber(phone);
            if(memberVO != null){
                // 일반 기존회원이면 수정
                // kakao로 로그인 정보가 있으면 수정할 필요가 없다.(숙제)
                memberVO.setSns_provider("naver");
                memberVO.setSns_email_naver(naverUserVO.getResponse().getEmail());
                memberService.updateKakaoInfo(memberVO);

            }else{
                // 기존회원이 아니면 삽입
                memberVO = new MemberVO();
                memberVO.setM_id(naverUserVO.getResponse().getId());
                memberVO.setM_name(naverUserVO.getResponse().getName());
                memberVO.setM_phone(phone);
                memberVO.setM_email(naverUserVO.getResponse().getEmail());
                memberVO.setSns_provider("naver");
                memberVO.setSns_email_naver(naverUserVO.getResponse().getEmail());
                memberService.getMembersSnsJoinOK(memberVO);
            }
            session.setAttribute("logInChk", "ok");
            session.setAttribute("mvo", memberVO);
            return "redirect:/shop/list";
        } catch (Exception e) {
            log.info("naverlogin error : {}", e.getMessage());
            return "members/loginForm";
        }
    }
}
