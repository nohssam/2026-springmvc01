package org.study.myproject01.guestbook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.study.myproject01.guestbook.vo.GuestBookVO;

import java.util.List;

@Mapper
public interface GuestBookMapper {
    List<GuestBookVO> selectAll();
    int insertGuestBook(GuestBookVO gvo);
    GuestBookVO selectDetail(GuestBookVO gvo);
    int deleteGuestBook(GuestBookVO gvo);
    int updateGuestBook(GuestBookVO gvo);
}
