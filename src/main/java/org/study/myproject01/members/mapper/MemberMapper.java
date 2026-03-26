package org.study.myproject01.members.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.study.myproject01.members.vo.MemberVO;

@Mapper
public interface MemberMapper {
    MemberVO getMemberDetail(MemberVO memberVO);
    int getMembersJoinOK(MemberVO memberVO);
    MemberVO findByPhoneNumber(String phone);
    void updateKakaoInfo(MemberVO memberVO);
    void getMembersSnsJoinOK(MemberVO mvo);
}
