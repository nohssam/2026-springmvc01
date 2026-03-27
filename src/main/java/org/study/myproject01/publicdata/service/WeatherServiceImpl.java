package org.study.myproject01.publicdata.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.study.myproject01.publicdata.vo.WeatherItemVO;

import java.util.List;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {
    // application.properties 참조
    @Value("${weather.api.key}")
    private String weatherApiKey ;

    @Override
    public List<WeatherItemVO> getWeatherFcst(int nx, int ny) {
        String apiUrl = buildApiUrl(nx, ny);
        String json = callApi(apiUrl);
        return parseWeatherData(json);
    }

    private String buildApiUrl(int nx, int ny) {
        return "";
    }
    private String callApi(String apiUrl) {
        return "";
    }
    private List<WeatherItemVO> parseWeatherData(String json){
        return null;
    }
}
