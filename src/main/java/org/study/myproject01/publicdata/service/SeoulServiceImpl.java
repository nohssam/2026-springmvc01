package org.study.myproject01.publicdata.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.study.myproject01.publicdata.vo.CulturalVO;
import org.study.myproject01.publicdata.vo.KorResVO;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SeoulServiceImpl implements SeoulService{
    // application.properties 참조
    @Value("${seoul.api.key}")
    private String seoulApiKey ;

    @Override
    public List<CulturalVO.Row> getCultural() {
        String apiUrl = "http://openapi.seoul.go.kr:8088/" +
                 seoulApiKey
                + "/xml/culturalEventInfo/1/10/"; // 1-10번째

        String xml = callApi(apiUrl) ;  // 서울시 공공데이터에 가서 xml 정보 가져오기
        return parseCulturalXml(xml);   // xml 파싱하기
    }

    @Override
    public List<KorResVO> getKorRes() {
        String apiUrl = "http://openapi.seoul.go.kr:8088/" +
                seoulApiKey
                + "/json/SebcKoreanRestaurantsKor/1/10/";  // 1-10번째

        String json = callApi(apiUrl) ;  // 서울시 공공데이터에 가서 xml 정보 가져오기
        return parseKorRes(json);        // json 파싱하기
    }

    private String callApi(String apiUrl){
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode >=200 && responseCode<=300
                    ? conn.getInputStream()
                    : conn.getErrorStream(),
                    StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try{
                if(reader != null){reader.close();}
                if(conn != null){conn.disconnect();}
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private List<CulturalVO.Row> parseCulturalXml(String xml){
        List<CulturalVO.Row> list = new ArrayList<>();
        if(xml == null){ return list;}

        try{
            // 1. XML 문자열 -> 바이트배열 -> InputStream -> DOM Document 객체
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            // 2. DOM 트리 정규화 : 분산된 텍스트 노드를 합쳐  getTextContent() 가 정확하게 동작하게 함
            doc.getDocumentElement().normalize();
            // 3. xml 소스를 보고 반복되는 row을 찾자
            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                // row 안에 있는 태그 추출
                Element el = (Element) rows.item(i);

                // 3. 각 태그의 텍스트 값 읽어 VO 에 저장
                CulturalVO.Row vo = new CulturalVO.Row();
                vo.setCODENAME(getXmlText(el, "CODENAME"));   // 분류
                vo.setGUNAME(getXmlText(el, "GUNAME"));       // 자치구
                vo.setTITLE(getXmlText(el, "TITLE"));         // 공연/행사명
                vo.setDATE(getXmlText(el, "DATE"));           // 날짜
                vo.setPLACE(getXmlText(el, "PLACE"));         // 장소
                vo.setORG_NAME(getXmlText(el, "ORG_NAME"));  // 기관명
                vo.setUSE_TRGT(getXmlText(el, "USE_TRGT"));  // 이용대상
                vo.setUSE_FEE(getXmlText(el, "USE_FEE"));    // 이용요금
                vo.setINQUIRY(getXmlText(el, "INQUIRY"));    // 문의
                vo.setPLAYER(getXmlText(el, "PLAYER"));      // 출연자정보
                vo.setPROGRAM(getXmlText(el, "PROGRAM"));    // 프로그램소개
                vo.setETC_DESC(getXmlText(el, "ETC_DESC"));  // 기타내용
                vo.setORG_LINK(getXmlText(el, "ORG_LINK"));  // 홈페이지 주소
                vo.setMAIN_IMG(getXmlText(el, "MAIN_IMG"));  // 대표이미지
                vo.setRGSTDATE(getXmlText(el, "RGSTDATE")); // 신청일
                vo.setTICKET(getXmlText(el, "TICKET"));      // 시민/기관
                vo.setSTRTDATE(getXmlText(el, "STRTDATE")); // 시작일
                vo.setEND_DATE(getXmlText(el, "END_DATE")); // 종료일
                vo.setTHEMECODE(getXmlText(el, "THEMECODE")); // 테마분류
                vo.setLOT(getXmlText(el, "LOT"));            // 경도
                vo.setLAT(getXmlText(el, "LAT"));            // 위도
                vo.setIS_FREE(getXmlText(el, "IS_FREE"));   // 유무료
                vo.setHMPG_ADDR(getXmlText(el, "HMPG_ADDR")); // 상세 URL
                vo.setPRO_TIME(getXmlText(el, "PRO_TIME")); // 행사시간
                list.add(vo);
            }
            return list;
        } catch (Exception e) {
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

    private List<KorResVO> parseKorRes(String json){
        List<KorResVO> list = new ArrayList<>();
        Gson gson = new Gson();
        if(json == null){ return list;}
        try{
            // 1. JSON 전체를 최상위 JSONObject로 변환
            JsonObject root = new Gson().fromJson(json, JsonObject.class);

            // 2. 중접 경로 탐색 : response -> body -> items -> item 배열 추출
            JsonArray rows = root.getAsJsonObject("SebcKoreanRestaurantsKor")
                    .getAsJsonArray("row");

            // rows가 배열이므로 반복해서 정보를 추출하자
            for (JsonElement k : rows) {
                KorResVO vo =  gson.fromJson(k, KorResVO.class);
                list.add(vo);
            }
            return list;
        }catch(Exception e){
            log.info(e.getMessage());
            return null;
        }
    }

}

