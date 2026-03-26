package org.study.myproject01.sns.service;

import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class KakaoLogoutUtil {
    private static final String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";

    public static void logoutByAccessToken(String accessToken){
       try{
           URL url = new URL(KAKAO_LOGOUT_URL);
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("POST");
           connection.setDoOutput(true);
           connection.setRequestProperty("Authorization",  "Bearer " + accessToken);

       }catch(Exception e){
           log.info(e.getMessage());
       }
    }
}
