<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ChatGPT 채팅</title>
  <style>
    body {
      font-family: 'Malgun Gothic', sans-serif;
      max-width: 800px;
      margin: 30px auto;
      padding: 0 20px;
      background: #f0f2f5;
    }
    h2 {
      color: #333;
      border-bottom: 2px solid #4a90d9;
      padding-bottom: 8px;
    }

    /* 대화 이력 컨테이너 */
    #chat-history {
      background: #fff;
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 15px;
      height: 420px;
      overflow-y: auto;        /* 스크롤 가능 */
      margin-bottom: 16px;
    }

    /* 개별 메시지 말풍선 */
    .msg {
      padding: 10px 14px;
      border-radius: 8px;
      margin-bottom: 10px;
      line-height: 1.6;
      white-space: pre-wrap;   /* 줄바꿈 유지 */
      word-break: break-word;
    }
    /* 사용자 메시지: 파란색 계열 */
    .msg.user {
      background: #e8f0fe;
      border-left: 4px solid #4a90d9;
    }
    /* AI 답변: 회색 계열 */
    .msg.assistant {
      background: #f6f6f6;
      border-left: 4px solid #aaa;
    }
    /* 시스템 메시지: 노란색 계열 */
    .msg.system {
      background: #fff3cd;
      border-left: 4px solid #f0ad4e;
      font-size: 0.9em;
    }

    /* 메시지 발신자 레이블 */
    .msg .label {
      font-weight: bold;
      font-size: 0.85em;
      color: #555;
      margin-bottom: 4px;
    }

    /* 입력 폼 영역 */
    #chat-form textarea {
      width: 100%;
      height: 100px;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 14px;
      resize: vertical;
      box-sizing: border-box;
    }
    #chat-form button {
      margin-top: 8px;
      padding: 8px 22px;
      background: #4a90d9;
      color: #fff;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-size: 14px;
    }
    #chat-form button:hover {
      background: #357abd;
    }

    /* 상단 버튼 영역 */
    .top-btns {
      display: flex;
      gap: 10px;
      margin-bottom: 14px;
    }
    .top-btns a, .top-btns button {
      padding: 6px 16px;
      border-radius: 4px;
      text-decoration: none;
      font-size: 13px;
      cursor: pointer;
    }
    .btn-logout {
      background: #e74c3c;
      color: #fff;
      border: none;
    }
    .btn-clear {
      background: #f39c12;
      color: #fff;
      border: none;
    }
  </style>
</head>
<body>

<h2>ChatGPT 채팅</h2>


<div class="top-btns">

  <a href="${pageContext.request.contextPath}/member/logout" class="btn-logout">로그아웃</a>


  <form method="post" action="${pageContext.request.contextPath}/chatClear" style="margin:0">
    <button type="submit" class="btn-clear">대화 초기화</button>
  </form>
</div>

<%-- 대화 이력 표시 영역 --%>
<div id="chat-history">
  <c:choose>
    <c:when test="${empty chatList}">

      <p style="color:#999; text-align:center; margin-top:40px;">
        이전 대화 내역이 없습니다.<br>아래에 질문을 입력해 주세요.
      </p>
    </c:when>
    <c:otherwise>

      <c:forEach var="chat" items="${chatList}">
        <div class="msg ${chat.role}">

          <div class="label">
            <c:choose>
              <c:when test="${chat.role == 'user'}">나</c:when>
              <c:when test="${chat.role == 'assistant'}">ChatGPT</c:when>
              <c:otherwise>System</c:otherwise>
            </c:choose>
          </div>

          <c:out value="${chat.content}"/>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>
</div>


<form id="chat-form" method="post"
      action="${pageContext.request.contextPath}/chatSend">

  <input type="hidden" name="conversation_id" value="latest"/>

  <textarea name="content"
            placeholder="질문을 입력하세요 (예: 스프링 부트란 무엇인가요?)"
            required></textarea>
  <br>
  <button type="submit">전송</button>
</form>

<script>
  var chatHistory = document.getElementById('chat-history');
  if (chatHistory) {
    chatHistory.scrollTop = chatHistory.scrollHeight;
  }
</script>

</body>
</html>
