package org.study.myproject01.gpt.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;


/*
대화 이력을 유지하기 위해서 테이블을 만든다.
매번 API 호출 할때 이전 대화 내용을 함께 보내줘야 문맥을 이어서 대화 할 수 있다.
사용자 입력 -> DB  저장 -> DB이전 대화 전체 조회 -> GPT API 전체 대화 이력 전송 -> GPT 답변

CREATE TABLE chat_messages (
  chat_idx BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id        VARCHAR(100) NOT NULL,
  conversation_id VARCHAR(64) NOT NULL,
  role ENUM('system','user','assistant') NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatVO {
    /** 채팅 메세지 고유 번호 **/
    private Long chat_idx;

    /** 사용자 ID **/
    private String user_id;

    private String conversation_id;

    // system : 시스템 지시, user:사용자, assistant: 답변
    private String role;

    // 메세지 내용
    private String content;

    private String created_at;
}
