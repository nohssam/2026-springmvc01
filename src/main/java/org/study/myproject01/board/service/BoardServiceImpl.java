package org.study.myproject01.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.bbs.vo.BBSVO;
import org.study.myproject01.board.mapper.BoardMapper;
import org.study.myproject01.board.vo.BoardVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;

    @Override
    public int getBoardCount() {
        return boardMapper.getBoardCount();
    }

    @Override
    public List<BoardVO> getBoardList(int numPerPage, int offset) {
        // DB가는 파라미터는 무조건 하나로 만들어야 한다.
        Map<String,Object> map = new HashMap<>();
        map.put("limit",numPerPage);
        map.put("offset",offset);
        return boardMapper.getBoardList(map);
    }

    @Override
    public int getBoardInsert(BoardVO boardVO) {
        return boardMapper.getBoardInsert(boardVO);
    }

    @Override
    public int getBoardHitUp(BoardVO boardVO) {
        return boardMapper.getBoardHitUp(boardVO);
    }

    @Override
    public BoardVO getBoardDetail(BoardVO boardVO) {
        return boardMapper.getBoardDetail(boardVO);
    }

    @Override
    public void getLevUp(Map<String, Integer> map) {
        boardMapper.getLevUp(map);
    }

    @Override
    public int getBoardAnsInsert(BoardVO boardVO) {
        return boardMapper.getBoardAnsInsert(boardVO);
    }

    @Override
    public void getDelete(BoardVO bvo) {
        boardMapper.getDelete(bvo);
    }
}
