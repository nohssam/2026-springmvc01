package org.study.myproject01.publicdata.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherFcstVO {
  // JSP에서 쓰기 좋게 가동한 것 (날짜/시간별 묶음)
    private String fcstDate;  // 예보일자
    private String fcstTime;  // 예보시각
    private String tmp;       // 온도
    private String sky;       // 하늘상태    맑음(1), 구름많음(3), 흐림(4)
    private String pty;       // 강수 형태   없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
    private String pop;       // 강수 확률 %
    private String pcp;       // 1시간 강수량
    private String reh;       // 습도
    private String wsd;       // 풍속
}
