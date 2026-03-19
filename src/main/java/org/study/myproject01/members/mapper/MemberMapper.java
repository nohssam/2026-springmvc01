package org.study.myproject01.members.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.study.myproject01.members.vo.MemberVO;

@Mapper
public interface MemberMapper {
    MemberVO getMemberDetail(MemberVO memberVO);
}
