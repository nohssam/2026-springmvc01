package org.study.myproject01.bbs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.study.myproject01.bbs.vo.BBSVO;
import org.study.myproject01.bbs.vo.CommentVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface BBSMapper {
    // 전체 게시물의 수
    int getCount();

    // 리스트
    // List<BBSVO> getBBSList();
    List<BBSVO> getBBSList(Map<String,Integer> map);

    // 원글 삽입
    int getBBSInsert(BBSVO bbsVO);
    // 원글 상세보기
    BBSVO getBBSDetail(BBSVO bbsVO);
    // 원글 삭제
    int deleteBBS(BBSVO bbsVO);
    // 원글 수정
    int updateBBS(BBSVO bbsVO);
    // 조회수 업데이트
    int getBBSHitUpdate(BBSVO bbsVO);
    // 댓글 조회
    List<CommentVO> getCommentList(BBSVO bbsVO);
    // 댓글 삽입
    int getCommentInsert(CommentVO commentVO);
    // 댓글 삭제
    int getCommentDelete(CommentVO commentVO);
    // 댓글 검색
    CommentVO getCommentDetail(CommentVO commentVO);
}
