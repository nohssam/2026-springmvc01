package org.study.myproject01.members.service;

import org.study.myproject01.members.vo.MemberVO;

public interface MemberService {
    MemberVO getMemberDetail(MemberVO memberVO);
    int getMembersJoinOK(MemberVO memberVO);
    MemberVO findByPhoneNumber(String phone);
    void updateKakaoInfo(MemberVO memberVO);
    void getMembersSnsJoinOK(MemberVO mvo);
}
