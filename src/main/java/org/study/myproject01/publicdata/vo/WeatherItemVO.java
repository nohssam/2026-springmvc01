package org.study.myproject01.publicdata.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherItemVO {
    private String baseDate ;  // 발표일자
    private String baseTime ;  // 발표시간
    private String category ;  // 자료구분(TMP, SKY, PTY....)
    private String fcstDate ;  // 예보일자
    private String fcstTime ;  // 예보시각
    private String fcstValue ; // 예보값
    private int nx ;           // 예보지점 X 좌표
    private int ny ;           // 예보지점 Y 좌표

}
