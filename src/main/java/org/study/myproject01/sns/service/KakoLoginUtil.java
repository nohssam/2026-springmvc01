package org.study.myproject01.sns.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.study.myproject01.sns.vo.KakaoUserVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class KakoLoginUtil {
    private static final String CLIENT_ID = "731257f60bb42c7add0730740e688679";
    private static final String CLIENT_SECRET = "ulEZ5ZUoKx4rh6CrM8DtEcBy0nMLVT70";
    private static final String REDIRECT_URI = "http://localhost:8080/myproject01/kakaologin";

    // 인가코드 보내서 Access Token 받기
    public static String getAccessToken(String code){
        try{
           String reqURL = "https://kauth.kakao.com/oauth/token";

           URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 서버로 데이터를 보내겠다.(요청 본문에 데이터를 쓸수 있게 한다.(post)
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // 요청을 보낼때 헤더를 설정
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 필요 파라미터 설정
            String param = "grant_type=authorization_code"
                    +"&client_id="+CLIENT_ID
                    +"&redirect_uri="+REDIRECT_URI
                    +"&code="+code
                    +"&client_secret="+CLIENT_SECRET;

            // 보내기
            try(OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(param);
                writer.flush();
            }

            // AccessToken 받기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String msg ;
            while((msg = br.readLine()) != null){
                sb.append(msg);
            }
            // 결과 보기 (String)
            // System.out.println(sb.toString());

            // JSON 방식으로 처리 하자
            // Gson 라이브러리 사용 (카카오/네이버 JSON 파싱) => pom.xml
            // JSON     -> java 객체  (fromJson())  현재 사용
            // java 객체 -> JSON      (toJson())
            Gson gson = new Gson();
            // AccessTokenResponse.class => access_token 파싱하는 내부클래스
            AccessTokenResponse tokenResponse = gson.fromJson(sb.toString(), AccessTokenResponse.class);
            return tokenResponse.getAccess_token();
        } catch (Exception e) {
            log.info("Access Token Error : {}", e.getMessage());
            return null;
        }
    }

    //  access_token 파싱하는 내부클래스
    private static class AccessTokenResponse{
        private String access_token;
        public String getAccess_token() {return access_token;}
        public void setAccess_token(String access_token) {this.access_token = access_token;}

    }

    // accessToken을 가지고 사용자 정보 받기
    public static KakaoUserVO getUserInfo(String accessToken){
        try{
            String reqURL =  "https://kapi.kakao.com/v2/user/me";

            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 서버로 데이터를 보내겠다.(요청 본문에 데이터를 쓸수 있게 한다.(post)
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // 요청을 보낼때 헤더를 설정
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String msg ;
            while((msg = br.readLine()) != null){
                sb.append(msg);
            }
            // 결과 보기 (String)
            // System.out.println(sb.toString());

            // JSON     -> java 객체  (fromJson())  현재 사용
            Gson gson = new Gson();

            // 자동으로 vo 안에 값이 들어간다.
            // KakaoUserVO kakaoUserVO = gson.fromJson(sb.toString(), KakaoUserVO.class);
            // return kakaoUserVO;
            return  gson.fromJson(sb.toString(), KakaoUserVO.class);
        } catch (Exception e) {
            log.info("UserInfo Error : {}", e.getMessage());
            return null;
        }
    }

}
