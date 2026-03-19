package org.study.myproject01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptConfig {
    // Spring에게 Bean 으로 등록
    // Bean이란 Spring 컨테이너가 생성하고 관리하는 객체를 말한다.
    // 어디서든지 @Autowired 로 주입 받아 사용 가능
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
