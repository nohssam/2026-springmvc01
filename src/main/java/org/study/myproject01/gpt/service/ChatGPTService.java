package org.study.myproject01.gpt.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ChatGPTService {
    @Value("${openai.api.key}")
    private String RAWAPIKEY;
    @Value("${openai.model}")
    private String RAWMODEL;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";


    public String chat(List<Map<String,String>> messages) throws Exception{
        // 1. 입력값 검증
        //  API 키 ; sk- 또는 sk-proj- 시작 해야 유효
        if(RAWAPIKEY == null || !RAWAPIKEY.startsWith("sk-")){
            throw new IllegalStateException(("유효한 apiKey가 없습니다."));
        }
        // 모델명 체크
        if(RAWMODEL == null || RAWMODEL.isBlank()){
            throw new IllegalStateException(("유효한 model가 없습니다."));
        }
        // 메세지 리스트 필수
        if(messages == null || messages.isEmpty()){
            throw new IllegalStateException(("messages 가 비어있습니다."));
        }

        // OkHttpClinet를 빌드
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)   // 전체 호출 시간 제한
                .readTimeout(2, TimeUnit.MINUTES)   // 응답 읽기 시간 제한
                .writeTimeout(2,TimeUnit.MINUTES)   // 요청 쓰기 시간 제한
                .connectTimeout(15,TimeUnit.MINUTES) // 서버 연결 시간 제한
                .retryOnConnectionFailure(true)              // 연결 실패 시 재시도
                .build();

        //  json으로 요청 바디 생성
        // 보내는 형식
        // { "model": "VAR_chat_model_id", "messages": [ { "role": "...", "content": "...." },...]}
        // 위 형식을 json을 만들자
        // "model": "VAR_chat_model_id"
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model",RAWMODEL);

        JsonArray jsonArray = new JsonArray();
        for(Map<String,String> k : messages){
            // 각 메세지에 role, content 필드가 있는지 검증
            if(!k.containsKey("role") || !k.containsKey("content")){
                throw new IllegalArgumentException("message 항목에 role 또는 content 가 없습니다.");
            }
            //  { "role": "...", "content": "...." }
            JsonObject item = new JsonObject();
            item.addProperty("role",k.get("role"));
            item.addProperty("content",k.get("content"));
            jsonArray.add(item);
        }
        //"messages": [ { "role": "...", "content": "...." },...]
        jsonObject.add("messages",jsonArray);

        // POST 요청 생성
        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json")
        );

        // 요청 방법
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer "+RAWAPIKEY)
                .post(body)
                .build();

        //  API 호출 및 응답 파싱
        try(Response response = client.newCall(request).execute()){
            String respBody = response.body().string();

            if(!response.isSuccessful()){
                throw new RuntimeException("OpenAI API 오류 : " + response.code() + "|" + respBody);
            }

            // 응답은 respBody 있다. (JSON 방식)
            // { "choices": [  { "index": 0,
            //                   "message": {  "role": "assistant", "content": "응답",
            //                                "refusal": null,   "annotations": []  },
            //                   "logprobs": null,
            //                    "finish_reason": "stop"  }]}

            JsonObject root = JsonParser.parseString(respBody).getAsJsonObject();
            return root.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();
        }
    }
}
