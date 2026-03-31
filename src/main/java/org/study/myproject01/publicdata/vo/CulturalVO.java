package org.study.myproject01.publicdata.vo;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CulturalVO {

    // 전체 데이터 개수
    private String list_total_count;

    // 결과 코드/메시지
    private Result RESULT;

    // 실제 데이터 목록
    private List<Row> row;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String CODE;      // 결과 코드
        private String MESSAGE;   // 결과 메시지
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Row {
        private String CODENAME;     // 분류
        private String GUNAME;       // 자치구
        private String TITLE;        // 공연/행사명
        private String DATE;         // 날짜
        private String PLACE;        // 장소
        private String ORG_NAME;     // 기관명
        private String USE_TRGT;     // 이용대상
        private String USE_FEE;      // 이용요금
        private String INQUIRY;      // 문의
        private String PLAYER;       // 출연자정보
        private String PROGRAM;      // 프로그램소개
        private String ETC_DESC;     // 기타내용
        private String ORG_LINK;     // 홈페이지 주소
        private String MAIN_IMG;     // 대표이미지
        private String RGSTDATE;     // 신청일
        private String TICKET;       // 시민/기관
        private String STRTDATE;     // 시작일
        private String END_DATE;     // 종료일
        private String THEMECODE;    // 테마분류
        private String LOT;          // 경도
        private String LAT;          // 위도
        private String IS_FREE;      // 유무료
        private String HMPG_ADDR;    // 상세 URL
        private String PRO_TIME;     // 행사시간
    }
}