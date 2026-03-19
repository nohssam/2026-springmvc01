package org.study.myproject01.board.service;

import org.study.myproject01.bbs.vo.BBSVO;
import org.study.myproject01.bbs.vo.CommentVO;
import org.study.myproject01.board.vo.BoardVO;

import java.util.List;
import java.util.Map;

public interface BoardService {
    // 전체 게시물의 수
    int getBoardCount();
    // 리스트
     List<BoardVO> getBoardList(int numPerPage, int offset);
     // 글 쓰기
     int getBoardInsert(BoardVO boardVO);
    // 조회수 증가
    int getBoardHitUp(BoardVO boardVO);
    // 상세보기 정보 가져오기
    BoardVO getBoardDetail(BoardVO boardVO);
    // 답글 처리를 위한 lev증가
    void getLevUp(Map<String,Integer> map);
    // 답글 삽입
    int getBoardAnsInsert(BoardVO boardVO);
    // 삭제
    void getDelete(BoardVO bvo);

}
