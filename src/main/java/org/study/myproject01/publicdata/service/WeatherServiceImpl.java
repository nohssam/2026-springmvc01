package org.study.myproject01.publicdata.service;

import ch.qos.logback.core.joran.spi.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.study.myproject01.publicdata.vo.WeatherFcstVO;
import org.study.myproject01.publicdata.vo.WeatherItemVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {
    // application.properties 참조
    @Value("${weather.api.key}")
    private String weatherApiKey ;

    @Override
    public List<WeatherFcstVO> getWeatherFcstJson(int nx, int ny) {
        String apiUrl = buildApiUrl(nx, ny,"JSON"); //URL 생성
        String json = callApi(apiUrl);              // HTTP 호출
        return parseWeatherDataJSON(json);              // JSON 파싱
    }

    @Override
    public List<WeatherFcstVO> getWeatherFcstXml(int nx, int ny) {
        String apiUrl = buildApiUrl(nx, ny,"XML");
        String xml = callApi(apiUrl);
        return parseWeatherDataXml(xml);   // XML 파싱
    }

    // 기상청 단기예보 하루 8번 발표 : 02,05,08,11,14,17,20,23 시
    // 각 발표는 해당 +10분 이후 부터 조회 가능

    // 처리 순서
    // 1. 현재 시간 조회
    // 2. baseTimes 배열을 역순으로 순회하며 "현재 >= 발표시각+10분 " 인 가장 최근 발표 시각 탐색
    // 3. 오늘 , 전날
    // 4. baseData(yyyyMMdd), baseTime(HHmm)
    private  String buildApiUrl(int nx, int ny,String dataType){
        // 1. 한국 시간대 기준 현재 시간
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        int[] baseTimes = {2,5,8,11,14,17,20,23};
        int baseHour = -1 ;
        // 2.  baseTimes 배열을 역순으로 순회
        for (int i=baseTimes.length-1; i>=0 ;i--) {
            // 발표 가능 시간 = 해당 발표 시 + 1 (0800 -> 0810)
            LocalDateTime baseDateTime = now.withHour(baseTimes[i]).withMinute(10).withSecond(0).withNano(0);
            //isBefore() 이전이면 true, 이후이거나 같으면 false
            if (!now.isBefore(baseDateTime)) {
                baseHour = baseTimes[i];
                break;
            }
        }
            // 아직 발표 시간이 아니면 전날 2300 발표 사용
            if(baseHour == -1){
                now = now.minusDays(1);
                baseHour = 23 ;
            }
            String basDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 20260330
            String baseTime = String.format("%02d00",baseHour);                   // 08
            log.info("baseTime:{}, dataType: {}",baseTime, dataType);
            return "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
                    + "?ServiceKey="+weatherApiKey
                    +"&pageNo=1"
                    +"&numOfRows=1000"
                    +"&dataType=" + dataType
                    +"&base_date=" +  basDate
                    +"&base_time=" +  baseTime
                    +"&nx=" + nx
                    +"&ny=" + ny;

    }

    private String callApi(String apiUrl){
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try{
           URL url = new URL(apiUrl);
           conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");
           conn.setRequestProperty("Content-type", "application/json");

           int responseCode = conn.getResponseCode();

           reader = new BufferedReader(new InputStreamReader(
                   responseCode==200 && responseCode <= 300  // 조건
                   ? conn.getInputStream()    //  정상응답
                   : conn.getErrorStream(),   //  오류응답
                   StandardCharsets.UTF_8

           ));

           StringBuilder sb = new StringBuilder();
           String line;
           while ((line = reader.readLine()) != null) {
               sb.append(line);
           }
           return sb.toString();

        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }finally {
            try {
                if (reader != null) {  reader.close();    }
                if (conn != null) { conn.disconnect();    }
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    // JSON 파싱
    private List<WeatherFcstVO> parseWeatherDataJSON(String json){
        if(json == null) return new ArrayList<>();
        try{
            // 1. JSON 전체를 최상위 JSONObject로 변환
            JsonObject root = new Gson().fromJson(json, JsonObject.class);

            // 2. 중접 경로 탐색 : response -> body -> items -> item 배열 추출
            JsonArray items = root.getAsJsonObject("response")
                    .getAsJsonObject("body")
                    .getAsJsonObject("items")
                    .getAsJsonArray("item");

            // 3. 날짜+시각을 키로, WeatherFcstVO 를 값을 하는 순서 보존 map
            // HashMap는 순서가 없다. LinkedHashMap는 순서를 유지 한다.
            Map<String, WeatherFcstVO> map = new LinkedHashMap<>();
            Gson gson = new Gson();

            for (JsonElement k : items) {
                // json을 자바 객체 로 만듬
                WeatherItemVO item = gson.fromJson(k, WeatherItemVO.class);

                // 날짜 , 시간 =>  2026032906 -> 2026-03-29  06:00 만들자
                String key = item.getFcstDate() + item.getFcstTime();

                // 키가 없으면 새로 만들어 주세요
                // map.computeIfAbsent(key, key -> 새로 만들값)
                map.computeIfAbsent(key, k1 -> {
                    WeatherFcstVO vo = new WeatherFcstVO();
                    vo.setFcstDate(item.getFcstDate());
                    vo.setFcstTime(item.getFcstTime());
                    return vo;
                });

                WeatherFcstVO vo = map.get(key);
                switch (item.getCategory()) {
                    case "TMP" : vo.setTmp(item.getFcstValue()); ; break;
                    case "SKY" : vo.setSky(item.getFcstValue()); ; break;
                    case "PTY" : vo.setPty(item.getFcstValue()); ; break;
                    case "POP" : vo.setPop(item.getFcstValue()); ; break;
                    case "PCP" : vo.setPcp(item.getFcstValue()); ; break;
                    case "REH" : vo.setReh(item.getFcstValue()); ; break;
                    case "WSD" : vo.setWsd(item.getFcstValue()); ; break;
                }
            }
            // map.values()를 List로 변환
            return new ArrayList<>(map.values());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    // XML 파싱
    private List<WeatherFcstVO> parseWeatherDataXml(String xml){
        if(xml == null) return new ArrayList<>();
        try{
            // 1. XML 문자열 -> 바이트배열 -> InputStream -> DOM Document 객체
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            // 2. DOM 트리 정규화 : 분산된 텍스트 노드를 합쳐  getTextContent() 가 정확하게 동작하게 함
            doc.getDocumentElement().normalize();
            // 3. xml 소스를 보고 반복되는 item을 찾자
            NodeList itemNodes = doc.getElementsByTagName("item");
            // 4. 날짜와 시각을 키로 WeatherFcstVO를 값으로 하는 순서 보존 Map
            Map<String, WeatherFcstVO> map = new LinkedHashMap<>();
            for (int i = 0; i < itemNodes.getLength(); i++) {
                // item 하나
                Element el = (Element) itemNodes.item(i);

                // item 안에 있는 태그 에서 값 추출
                String category = getXmlText(el, "category") ;
                String fcstDate = getXmlText(el, "fcstDate") ;
                String fcstTime = getXmlText(el, "fcstTime") ;
                String fcstValue = getXmlText(el, "fcstValue") ;

                // JSON  방식과 동일하게 그룹화 키 사용
                String key = fcstDate + fcstTime;
                map.computeIfAbsent(key, k->{
                    WeatherFcstVO vo = new WeatherFcstVO();
                    vo.setFcstDate(fcstDate);
                    vo.setFcstTime(fcstTime);
                    return vo;
                });

                WeatherFcstVO vo = map.get(key);
                switch (category) {
                    case "TMP" : vo.setTmp(fcstValue); ; break;
                    case "SKY" : vo.setSky(fcstValue); ; break;
                    case "PTY" : vo.setPty(fcstValue); ; break;
                    case "POP" : vo.setPop(fcstValue); ; break;
                    case "PCP" : vo.setPcp(fcstValue); ; break;
                    case "REH" : vo.setReh(fcstValue); ; break;
                    case "WSD" : vo.setWsd(fcstValue); ; break;
                }

            }
            // map.values()를 List로 변환
            return new ArrayList<>(map.values());
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }
    }

    private String getXmlText(Element el, String tagName){
       NodeList nl =  el.getElementsByTagName(tagName);
       if(nl.getLength() == 0){
           return "";
       }else{
           // 태그 사이에 글자 추출 ;
         return nl.item(0).getTextContent().trim();
       }
    }
}
