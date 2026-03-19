package org.study.myproject01.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.bbs.mapper.BBSMapper;
import org.study.myproject01.bbs.vo.BBSVO;
import org.study.myproject01.bbs.vo.CommentVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BBSServiceImpl implements BBSService {
    @Autowired
    private BBSMapper bbsMapper;

    @Override
    public int getCount() {
        return bbsMapper.getCount();
    }

//    @Override
//    public List<BBSVO> getBBSList() {
//        return bbsMapper.getBBSList();
//    }

    @Override
    public List<BBSVO> getBBSList(int numPerPage, int offset) {
        // 파라미터가 여러개 일 경우 vo나 map을 사용해야 된다.
        Map<String,Integer> map = new HashMap<>();
        map.put("numPerPage",numPerPage);
        map.put("offset",offset);
        return bbsMapper.getBBSList(map);
    }

    @Override
    public int getBBSInsert(BBSVO bbsVO) {
        return bbsMapper.getBBSInsert(bbsVO);
    }

    @Override
    public BBSVO getBBSDetail(BBSVO bbsVO) {
        return bbsMapper.getBBSDetail(bbsVO);
    }

    @Override
    public int deleteBBS(BBSVO bbsVO) {
        return bbsMapper.deleteBBS(bbsVO);
    }

    @Override
    public int updateBBS(BBSVO bbsVO) {
        return bbsMapper.updateBBS(bbsVO);
    }

    @Override
    public int getBBSHitUpdate(BBSVO bbsVO) {
        return bbsMapper.getBBSHitUpdate(bbsVO);
    }

    @Override
    public List<CommentVO> getCommentList(BBSVO bbsVO) {
        return bbsMapper.getCommentList(bbsVO);
    }

    @Override
    public int getCommentInsert(CommentVO commentVO) {
        return bbsMapper.getCommentInsert(commentVO);
    }

    @Override
    public int getCommentDelete(CommentVO commentVO) {
        return bbsMapper.getCommentDelete(commentVO);
    }

    @Override
    public CommentVO getCommentDetail(CommentVO commentVO) {
        return bbsMapper.getCommentDetail(commentVO);
    }
}
