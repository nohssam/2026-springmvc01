package org.study.myproject01.email.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.myproject01.email.service.EmailService;

// Ajax용
@RestController
public class EmailAPIController {
    @Autowired
    private EmailService emailService;

    @RequestMapping("/sendCode")
    public String sendCode(String email, HttpSession session){
        try{
            // 실제 해당 이메일 전송 코드를 보내야 한다.(서비스에서 보낸다.)
            String code = emailService.sendAuthMail(email);
            session.setAttribute("code",code);
            session.setAttribute("time",System.currentTimeMillis());
            session.setAttribute("email",email);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }
    @RequestMapping("/verifyCode")
    public String verifyCode(String code, HttpSession session){
      try{
          String savecode = (String)session.getAttribute("code");
          Long savetime = (Long)session.getAttribute("time");
          // 시간 초과
          if((System.currentTimeMillis()-savetime) > (60*5*1000) ) {
              return "expired";
          }
          if(savecode.equals(code)) {
              // 일회용
              session.removeAttribute("code");
              return "match";
          }
          return "mismatch";

      }catch (Exception e){
          e.printStackTrace();
          return null;
      }
    }


}
