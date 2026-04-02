package org.study.myproject01.gpt.service;

import org.study.myproject01.gpt.vo.ChatVO;

import java.util.List;

public interface ChatService {
    List<ChatVO> getChatList(String user_id);
    void getChatInsert(ChatVO chatVO);
}
