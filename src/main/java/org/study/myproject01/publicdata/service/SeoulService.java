package org.study.myproject01.publicdata.service;

import org.study.myproject01.publicdata.vo.CulturalVO;
import org.study.myproject01.publicdata.vo.KorResVO;

import java.util.List;

public interface SeoulService {
    List<CulturalVO.Row> getCultural();
    List<KorResVO> getKorRes();
}
