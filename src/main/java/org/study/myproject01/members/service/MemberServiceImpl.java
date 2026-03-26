package org.study.myproject01.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.members.mapper.MemberMapper;
import org.study.myproject01.members.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberVO getMemberDetail(MemberVO memberVO) {
        return memberMapper.getMemberDetail(memberVO);
    }

    @Override
    public int getMembersJoinOK(MemberVO memberVO) {
        return memberMapper.getMembersJoinOK(memberVO);
    }

    @Override
    public MemberVO findByPhoneNumber(String phone) {
        return memberMapper.findByPhoneNumber(phone);
    }

    @Override
    public void updateKakaoInfo(MemberVO memberVO) {
        memberMapper.updateKakaoInfo(memberVO);
    }

    @Override
    public void getMembersSnsJoinOK(MemberVO mvo) {
        memberMapper.getMembersSnsJoinOK(mvo);
    }
}
