package org.study.myproject01.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    // 컨트롤러 가기 전에 체크하자
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 현재 요청에서 기존 세션 가져오기 (없으면 새로 생성 하지 않음)
        HttpSession session = request.getSession(false);

        // 로그인 성공 :  session.setAttribute("logInChk","ok");
        if(session != null && "ok".equals(session.getAttribute("logInChk"))){
            // 로그인 성공 상태 ->  요청 허용 ( controller에 갈 수 있다.)
            return true;
        }

        log.info("미로그인 접근 차단 : {}", request.getRequestURI());
        // 미로그인 ->  로그인 페이지로 리다이렉트
        // 서버 -> 브라우저 : 302 Found, /member/loginForm으로 가세요 안내함
        // 브라우저가 ->  서버 : /member/loginForm 새 요청

        // loginForm 에 ("logInChk", "required") 검사 하는 것이 있음
        HttpSession newsession = request.getSession(true);
        newsession.setAttribute("logInChk", "required");
        response.sendRedirect(request.getContextPath()+"/member/loginForm");
        return false;
    }
}
