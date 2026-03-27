package org.study.myproject01.sns.service;

import lombok.extern.slf4j.Slf4j;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class NaverLogoutUtil {
    private static final String CLIENT_ID = "jt_OkWhi_woj_DEH_d4d";
    private static final String CLIENT_SECRET = "7bs8x3csbc";
    private static final String NAVER_TOKEN_API = "https://nid.naver.com/oauth2.0/token";

    public static void logoutByAccessToken(String accesToken){
        HttpURLConnection connection =  null;
        try{
           URL url = new URL(NAVER_TOKEN_API);
           connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("POST");
           connection.setDoOutput(true);
           // 필요 파라미터 설정
           String param = "grant_type=delete"
                   +"&client_id="+CLIENT_ID
                   +"&client_secret="+CLIENT_SECRET
                   +"&access_token="+accesToken
                   +"&service_provider=NAVER";

           // 보내기
           try(OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
               writer.write(param);
               writer.flush();
           }

           int responseCode = connection.getResponseCode();
           if(responseCode == HttpURLConnection.HTTP_OK){
               log.info("Naver Logout Success : {} " , responseCode);
           }else{
               log.info("Naver Logout Error : {} " , responseCode);
           }

       }catch(Exception e){
           log.info(e.getMessage());
       } finally {
           if(connection != null) connection.disconnect();
       }
    }
}
