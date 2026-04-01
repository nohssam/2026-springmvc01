package org.study.myproject01.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//환경설정파일의 의미가 있음
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.upload2.path}")
    private String uploadPath2 ;

    // /upload2/** 요청을  src/main/resources/upload/ 파일 시스템 경로 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // windows와 Linux/Mac 이 경로 구분자가 다르기 때문에 통일 시키는 코드
        String fullPath = System.getProperty("user.dir").replace("\\", "/")+ "/"+ uploadPath2;

        //  /upload2/** 요청을  src/main/resources/upload/ 파일 시스템 경로 매핑
        registry.addResourceHandler("/upload2/**")
                .addResourceLocations("file:"+fullPath);
    }
    // 인터셉터 적용
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(new LoginInterceptor())
//               .addPathPatterns("/**")              // 모든 경로에 적용
//               .excludePathPatterns(
//                       // 인터셉터 제외되는 패턴(컨트롤러에 들어가는 패턴)
//                       "/",   // 메인페이지 (첫 페이지)
//                       "/member/loginForm",
//                       "/member/loginok",
//                       "/member/joinForm",
//                       "/member/joinok",
//                       "/kakaologin",
//                       "/naverlogin",
//                       "/sendCode",
//                       "/verifyCode",
//                       "/resources/**",
//                       "/upload2/**",
//                       "/images/**",
//                       "/css/**",
//                       "/js/**",
//                       "/shop/list",     // shop 리스트
//                       "/shop/detail"    // shop 상세보기
//
//               );
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList = List.of(
                "/",   // 메인페이지 (첫 페이지)
                       "/member/loginForm",
                       "/member/loginok",
                       "/member/joinForm",
                       "/member/joinok",
                       "/kakaologin",
                       "/naverlogin",
                       "/sendCode",
                       "/verifyCode",
                       "/resources/**",
                       "/upload2/**",
                       "/images/**",
                       "/css/**",
                       "/js/**",
                       "/shop/list",     // shop 리스트
                       "/shop/detail",    // shop 상세보기
                       "/bbs/**",          // bbs 는 모두 풀어줌
                       "/weather",
                       "/weatherFcst",
                        "/weatherJsonForm",
                        "/weatherXmlForm",
                        "/weatherJson",
                        "/weatherXml",
                        "/culturalEventInfo",
                        "/cultural",
                        "/KorRes",
                        "/translate",
                        "/translateOk"
        );
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")              // 모든 경로에 적용
                .excludePathPatterns(excludeList);
    }
}
