package org.study.myproject01.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
