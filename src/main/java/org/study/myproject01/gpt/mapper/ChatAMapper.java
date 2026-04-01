package org.study.myproject01.gpt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.study.myproject01.gpt.vo.ChatVO;

import java.util.List;

@Mapper
public interface ChatAMapper {
    List<ChatVO> getChatList(ChatVO chatVO);
    int sendUserMessage(ChatVO chatVO);
}
