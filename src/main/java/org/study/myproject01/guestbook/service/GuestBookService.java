package org.study.myproject01.guestbook.service;

import org.study.myproject01.guestbook.vo.GuestBookVO;

import java.util.List;

public interface GuestBookService {
    // DB 가는 메서드 ( = GuestBookMapper)
    List<GuestBookVO> selectAll();

    // 자체 일처리하는 비즈니스 로직
    // 오늘의 운세 구하기
    String getLuck();

    int insertGuestBook(GuestBookVO gvo);
    GuestBookVO selectDetail(GuestBookVO gvo);
    int deleteGuestBook(GuestBookVO gvo);
    int updateGuestBook(GuestBookVO gvo);
}
