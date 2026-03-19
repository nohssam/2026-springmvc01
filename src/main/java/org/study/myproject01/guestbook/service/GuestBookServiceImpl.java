package org.study.myproject01.guestbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.guestbook.mapper.GuestBookMapper;
import org.study.myproject01.guestbook.vo.GuestBookVO;

import java.util.List;

@Service
public class GuestBookServiceImpl implements  GuestBookService {
    @Autowired
    private GuestBookMapper guestBookMapper;

    // DB가는것
    @Override
    public List<GuestBookVO> selectAll() {
        return guestBookMapper.selectAll();
    }

    // DB 안 가고 일처리
    @Override
    public String getLuck() {
        int luck = (int)(Math.random()*101);
        return luck + "%";
    }

    @Override
    public int insertGuestBook(GuestBookVO gvo) {
        return guestBookMapper.insertGuestBook(gvo);
    }

    @Override
    public GuestBookVO selectDetail(GuestBookVO gvo) {
        return guestBookMapper.selectDetail(gvo);
    }

    @Override
    public int deleteGuestBook(GuestBookVO gvo) {
        return guestBookMapper.deleteGuestBook(gvo);
    }

    @Override
    public int updateGuestBook(GuestBookVO gvo) {
        return guestBookMapper.updateGuestBook(gvo);
    }
}
