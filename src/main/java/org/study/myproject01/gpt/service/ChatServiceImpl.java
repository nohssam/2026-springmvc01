package org.study.myproject01.gpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.gpt.mapper.ChatAMapper;
import org.study.myproject01.gpt.vo.ChatVO;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatAMapper chatAMapper;

    @Override
    public List<ChatVO> getChatList(String user_id) {
        ChatVO param = new ChatVO();
        param.setUser_id(user_id);
        // 특정 사용자의 대화 이력 조회
        // 현재 진행 중인 대화 이력 반환
        param.setConversation_id("latest"); // 현재 다일 대화 세션 latest 고정
        return  chatAMapper.getChatList(param);
    }

    @Override
    public int sendUserMessage(ChatVO chatVO) {
        // 1. 사용자 메세지 DB  저장

        // 2. 대화 이력 조회

        // 3. OPenAI API 호출용 message 리스트 구성

        // 4. 시스템 지시어 : AI에게ㅔ 한국어 답변 강제

        // 5.DB에서 조회한 대화 이력 모두 추가 (user, assistant)

        // 6. ChatGPT API 호출 (ChatGPTService 사용)


        // 7. AI 답변 DB 저장
        return 0;
    }
}
