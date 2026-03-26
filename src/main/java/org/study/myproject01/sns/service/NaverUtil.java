package org.study.myproject01.sns.service;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.study.myproject01.sns.vo.KakaoUserVO;
import org.study.myproject01.sns.vo.NaverUserVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class NaverUtil {
    private static final String CLIENT_ID = "jt_OkWhi_woj_DEH_d4d";
    private static final String CLIENT_SECRET = "7bs8x3csbc";

    // 인가코드를 받아서 AccessToken 받기
    public static String getAccessToken(String code, String state){
        try{
            String reqURL = "https://nid.naver.com/oauth2.0/token";

            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 서버로 데이터를 보내겠다.(요청 본문에 데이터를 쓸수 있게 한다.(post)
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // 필요 파라미터 설정
            String param = "grant_type=authorization_code"
                    +"&client_id="+CLIENT_ID
                    +"&client_secret="+CLIENT_SECRET
                    +"&code="+code
                    +"&state="+state;

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
            log.info("naver access token error : {}", e.getMessage());
            return null;
        }

    }

    // 받은 json을 자바 객체로 만들어 주는 클래스
    @Getter
    @Setter
    private static class AccessTokenResponse{
        private String access_token;
    }

    // accessToken을 받아서 사용자 정보 가져오기
    public static NaverUserVO getUserInfo(String accessToken){
        try{
            String reqURL = "https://openapi.naver.com/v1/nid/me";

            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 서버로 데이터를 보내겠다.(요청 본문에 데이터를 쓸수 있게 한다.(post)
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // 요청을 보낼때 헤더를 설정
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            // AccessToken 받기
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
            return  gson.fromJson(sb.toString(), NaverUserVO.class);
        } catch (Exception e) {
            log.info("naver access token error : {}", e.getMessage());
            return null;
        }
    }
}
