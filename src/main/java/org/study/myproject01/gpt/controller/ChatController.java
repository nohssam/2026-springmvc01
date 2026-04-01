package org.study.myproject01.gpt.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.study.myproject01.gpt.service.ChatService;
import org.study.myproject01.gpt.vo.ChatVO;
import org.study.myproject01.members.vo.MemberVO;

import java.util.List;

@Controller
public class ChatController {
   @Autowired
   private ChatService chatService;
   @GetMapping("/chatGPT")
   public String chatGPT(HttpSession session, Model model) {
        // 로그인 한 상태이면 사용자 정보를 추출 할 수 있다.
        MemberVO mvo = (MemberVO)session.getAttribute("mvo");
        String user_id = mvo.getM_id();

        // 해당 사용자의 대화 이력 조회 (최근 30개, 시간 오름차순)
        List<ChatVO> chatList = chatService.getChatList(user_id);

        model.addAttribute("chatList", chatList);
        return "chat/inputForm";
   }

   @PostMapping("/chatSend")
    public String chatSend(ChatVO chatVO, HttpSession session) {
       // 로그인 한 상태이면 사용자 정보를 추출 할 수 있다.
       MemberVO mvo = (MemberVO)session.getAttribute("mvo");
       chatVO.setUser_id(mvo.getM_id());
       chatVO.setRole("user");

       chatService.sendUserMessage(chatVO);

       return "redirect:/chatGPT";
   }
}
