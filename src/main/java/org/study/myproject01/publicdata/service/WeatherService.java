package org.study.myproject01.publicdata.service;

import org.study.myproject01.publicdata.vo.WeatherItemVO;

import java.util.List;

public interface WeatherService {
    List<WeatherItemVO> getWeatherFcst(int nx, int ny);
}
