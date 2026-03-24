<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <div>
    <input type="email" id="email" placeholder="등록된 이메일 입력" >
    <button id="sendCode">인증코드 전송</button>
  </div>
  <div>
    <input type="text" id="code" placeholder="인증코드 입력" >
    <button id="verifyCode">인증 확인</button>
  </div>
  <br>
  <br>
  <div id="result"></div>

  <div id="pw-change-form" style="display: none;">
    <input type="password" id="newPw" placeholder="새 비밀번호 입력" >
    <button onclick="changePw()">비밀번호 변경</button>
  </div>

  <script src="https://code.jquery.com/jquery-4.0.0.js"></script>
  <script>
    $("#sendCode").on("click", function() {
      const email = $("#email").val().trim();
      if(email === ""){
        alert("이메일을 입력하세요");
        $("#email").focus();
        return;
      }
      // 서버에 보내고 받아야 하므로 자바에서 별도의 Ajax용 컨트롤러를 만들어야 한다.
      $.ajax({
        url: "${pageContext.request.contextPath}/sendCode",
        method: "POST",
        data: {"email": email},
        dataType: "text",
        success: function(data){
          if(data == "success"){
            alert("인증코드 전송");
          }else{
            alert("전송 실패");
          }
        },
        error: function() {
          alert("읽기 실패");
        }
      });
    });

    $("#verifyCode").on("click", function() {
      const code = $("#code").val().trim();
      if(code === ""){
        alert("인증코드 입력하세요");
        $("#code").focus();
        return;
      }
      // 코드 검증
      $.ajax({
        url: "${pageContext.request.contextPath}/verifyCode",
        method: "POST",
        data: {"code": code},
        dataType: "text",
        success: function(data){
            let msg = {
              match : "인증성공",
              mismatch : "코드불일치",
              expired: "인증 시간 초과"
            };
            $("#result").html(msg[data] || "오류");

            // 만약에 비밀번호 변경
            if(data == "match"){
              $("#pw-change-form").show();
            }
        },
        error: function() {
          alert("인증실패");
        }
      })
    });

  </script>
</body>
</html>
