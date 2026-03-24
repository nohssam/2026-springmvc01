package org.study.myproject01.email.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.servlet.Session;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Properties;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${mail.sender.name}")
    private String senderName;

    private static final SecureRandom RANDOM = new SecureRandom();

    // 숫자 6자리 인증 코드 생성
    public String sendAuthMail(String email){
        // RANDOM.nextInt(900000) : 0 - 899999
        // 100000 +  RANDOM.nextInt(900000) = 100000 + 999999
        String code = String.valueOf(100000 + RANDOM.nextInt(900000));
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
            helper.setFrom(new InternetAddress(fromEmail, senderName,"utf-8"));
            helper.setTo(email);
            helper.setSubject("[회원가입] 이메일 인증 번호");
            // 일반 글자
            // helper.setText("인증번호:"+code+ "5분 이내에 입력해주세요.");

            // html
            String html = "<div style='font-family:sans-serif; max-width:500px; "
                    + "margin:auto; border:1px solid #ddd; border-radius:8px; padding:30px;'>"
                    +                    "<h2 style='color:#333;'>이메일 인증</h2>"
                    +                    "<p>아래 인증번호를 입력해주세요.</p>"
                    +                "<div style='font-size:32px; font-weight:bold; letter-spacing:8px; color:#4CAF50;                  +padding:20px; background:#f5f5f5; border-radius:4px; text-align:center;'>"
                    +                     code
                    +                     "</div>"
                    +                     "<p style='color:#888; margin-top:20px;'> 5분 이내에 입력해주세요.</p>"
                    +                     "</div>";
            helper.setText(html, true);
            mailSender.send(message);
            return code;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
