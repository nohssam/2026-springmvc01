package org.study.myproject01.gpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.gpt.mapper.ChatAMapper;
import org.study.myproject01.gpt.vo.ChatVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatAMapper chatAMapper;
    @Autowired
    private ChatGPTService chatGPTService;

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
    public void getChatInsert(ChatVO chatVO) {
        // 1. 사용자 메세지 DB  저장
        chatAMapper.getChatInsert(chatVO);

        // 2. 대화 이력 조회
        List<ChatVO> history = chatAMapper.getChatList(chatVO);

        // 3. OPenAI API 호출용 message 리스트 구성
        List<Map<String,String>> messages = new ArrayList<>();

        // 4. 시스템 지시어 : AI에게 한국어 답변 강제
        Map<String,String> systemMsg = new HashMap<>();
        systemMsg.put("role","system");
        systemMsg.put("content","대답은 한국어로 해");
        messages.add(systemMsg);

        // 5.DB에서 조회한 대화 이력 모두 추가 (user, assistant)
        // => 이전내용을 보내야 GPT가 문맥을 이해한다.
        for (ChatVO k : history) {
            Map<String,String> msg = new HashMap<>();
            msg.put("role",k.getRole());
            msg.put("content",k.getContent());
            messages.add(msg);
        }

        // 6. ChatGPT API 호출 (ChatGPTService 사용)
        String answer;
        try{
            answer = chatGPTService.chat(messages);
        } catch (Exception e) {
            answer = "[ChatGPT 오류]" + e.getMessage();
        }

        // 7. AI 답변 DB 저장
        ChatVO answerVO = new ChatVO();
        answerVO.setUser_id(chatVO.getUser_id());
        answerVO.setConversation_id("latest");
        answerVO.setRole("assistant");
        answerVO.setContent(answer);
        chatAMapper.getChatInsert(answerVO);

    }
}
