package org.study.myproject01.publicdata.service;

import org.study.myproject01.publicdata.vo.WeatherFcstVO;
import org.study.myproject01.publicdata.vo.WeatherItemVO;

import java.util.List;

public interface WeatherService {
    List<WeatherFcstVO> getWeatherFcstJson(int nx, int ny);
    List<WeatherFcstVO> getWeatherFcstXml(int nx, int ny);
}
