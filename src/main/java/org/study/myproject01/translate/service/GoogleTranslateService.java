package org.study.myproject01.translate.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleTranslateService {

    private static final String ENDPOINT = "https://translation.googleapis.com/language/translate/v2";
    @Value("${gcp.translate.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    // @param text        : 번역할 원문
    // @param targetLang  : 타켓언어 ("ko"=한국어), 기본값 "en" 적용
    // @param sourceLang  : 소스언어 ("en","vi"),  기본값은 Google 자동 감지
    public String getTranslate(String text, String targetLang, String sourceLang){
        //  타켓
        if(targetLang == null || targetLang.isBlank()){
            targetLang = "en";  // 미지정시 영어로 번역
        }

        // Form 형태로 만들어서 google에 보내자 (google은 POST 방식)
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("q", text)
                .add("target", targetLang)
                .add("format", "text") // 응답 형식이 일반 텍스트이다.
                .add("key", apiKey);

        // 소스언어: 지정시 추가, 미지정 시 Google 자동 감지
        if(sourceLang != null && !sourceLang.isBlank()){
            formBuilder.add("source", sourceLang);
        }

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(formBuilder.build())
                .build();

        // API 호출 및 응답 파싱
        try(Response resp = client.newCall(request).execute()){
            String body = resp.body().string();

            // HTTP 오류 처리
            if(!resp.isSuccessful()){
                throw  new RuntimeException("Google API 오류 : " + resp.code() + "|" + body);
            }

            // 응답 형태 (JSON)
            // { "data": { "translations": [{"translateText":"..." }]}},
            JsonObject root = JsonParser.parseString(body).getAsJsonObject();
            JsonArray arr  = root.getAsJsonObject("data")
                                  .getAsJsonArray("translations");

            return arr.get(0).getAsJsonObject().get("translatedText").getAsString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
