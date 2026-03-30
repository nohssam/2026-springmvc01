package org.study.myproject01.publicdata.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.study.myproject01.publicdata.service.WeatherService;
import org.study.myproject01.publicdata.vo.WeatherFcstVO;
import org.study.myproject01.publicdata.vo.WeatherItemVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class PublicDataApiController {

    @Autowired
    private WeatherService weatherService;

//    기상청 단기 예보
//    @GetMapping("/weatherFcst")
//    public List<WeatherItemVO> getWeatherData(
//            @RequestParam(defaultValue = "60") int nx,
//            @RequestParam(defaultValue = "127") int ny){
//        return weatherService.getWeatherFcst(nx, ny);
//    }


    @GetMapping("/weatherFcst")
    public String getWeatherData() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=7Jn37duPnqJP2hXtNvhcywuZlcu2XWgEJYHRSJIIwWps7J94qVJ8gOWdJOJSqoQ9rH2YQCMaCFMtlFsxFPAv8A=="); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("20", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20260329", "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0800", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("60", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line+"<br>");
        }
        rd.close();
        conn.disconnect();
        // System.out.println(sb.toString());
        return sb.toString();
    }

    // 기본 nx, ny는 서울 격자 좌표
    // RestController 이기 때문에 JSP로 가는것이 아니라 브라우저에 출력하고 있음
    // jsp는 ajax를 이용해서 정보를 가져간다.
    @GetMapping("/weatherJson")
    public List<WeatherFcstVO> weatherJSON(
     @RequestParam(defaultValue = "60")  int nx,
     @RequestParam(defaultValue = "127")  int ny ) {
        log.info("ajax 들어오나요");
        return weatherService.getWeatherFcstJson(nx, ny);
    }

    @GetMapping("/weatherXml")
    public List<WeatherFcstVO> weatherXml(
            @RequestParam(defaultValue = "60")  int nx,
            @RequestParam(defaultValue = "127")  int ny ) {
        return weatherService.getWeatherFcstXml(nx, ny);
    }

}
