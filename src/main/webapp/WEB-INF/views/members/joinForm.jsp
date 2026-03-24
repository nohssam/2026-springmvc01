<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#container{
		width: 500px;
		margin: 0 auto;   /* 중앙위치 */
		text-align: left; /* 내부 폼요소 왼쪽 정렬 */
	}
	h2{ text-align: center;	}
	fieldset {
		margin: 20px 0;
		padding: 20px;
		border: 1px solid #ccc;
    }
    legend {font-weight: bold;}
    ul{
    	list-style-type: none;
    	padding: 0;
    	margin: 0;
    }
    li {
    	display: flex;
    	align-items: center;
    	margin: 10px 0;
    }
    li label{
    	width: 120px;
    	flex-shrink: 0;
    }
    input {
    	padding: 5px;
    	flex: 1;
    	box-sizing: border-box;
    }
    li input[type="button"]{
    	margin-left: 10px;
    }
    #buttons{
    	text-align: center;
    	margin-top: 20px;
    }
    input[type="submit"],
    input[type="reset"]{
    	width: 100px;
    }
    a{text-decoration: none;}
</style>
</head>
<body>
	<%@ include file="../shop/top.jsp" %>
	<div id="container">
		<h2>회원가입</h2>
		<div id="login" style="margin: 30px;">
			<%--onsubmit="return checkForm();"  => submit 하기 전에 	checkForm() 실행 해서 true가 나와 야지 submit 실행	--%>
			<form action="${pageContext.request.contextPath}/member/joinok" method="post" autocomplete="off" onsubmit="return checkForm();">
				<fieldset>
					<legend>가입정보</legend>
					<ul>
					     <!-- 아이디 중복검사 필수 (ajax) -->
						<li><label for="m_id">아이디 : </label>
						    <input type="text" id="m_id" name="m_id" required>
						</li>   
						<li><label for="m_pw">비밀번호 :</label> 
							<input type="password" id="m_pw" name="m_pw" required>
						</li>	
						<li><label for="m_pw2">비밀번호확인 :</label> 
							<input type="password" id="m_pw2" name="m_pw2" required>
						</li>
						<li><label for="m_name">이름 : </label>
						    <input type="text" id="m_name" name="m_name" required>
						</li>   
						<li><label for="m_addr">주소 : </label>
						    <input type="text" id="m_addr" name="m_addr" required readonly>
						    <input type="button" value="주소찾기" onclick="execDaumPostcode()">
						</li>  
						<li style="padding-left: 120px;">
						    <input type="text" id="m_addr2" name="m_addr2" placeholder="상세주소입력">
						</li>    	
						<li><label for="m_email">email : </label>
						    <input type="text" id="m_email" name="m_email" required>
						    <input type="button" value="인증번호 보내기" onclick="sendVerificationCode()">
						</li>   
						<li><label for="emailCode">인증번호 : </label>
						    <input type="text" id="emailCode" placeholder="인증번호 입력">
						    <input type="button" value="확인" onclick="checkVerificationCode()">
						</li> 
						<li style="padding-left: 120px;">
							<span id="result"></span>
						</li>
						<li><label for="m_phone">phone : </label>
						    <input type="text" id="m_phone" name="m_phone" required disabled>
						</li>   
					</ul>
					<div id="buttons">
						<input type="submit" value="가입하기" id="submitBtn" disabled>
						<input type="reset" value="취소">
					</div>
				</fieldset>
			</form>
		</div>
	</div>
    <%-- ajax--%>
	<script src="https://code.jquery.com/jquery-4.0.0.js"></script>
    <%-- kakao 주소록   --%>
	<script src="//t1.kakaocdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
		 function checkForm() {
			 const pw = $("#m_pw").val().trim();
			 const pw2 = $("#m_pw2").val().trim();
			 if(pw === pw2){
				 return true;
			 }else{
				 alert("비밀번호가 일치하지 않습니다");
				 // $("#m_pw").val("").focus();
				 // $("#m_pw2").val("");

				 $("#m_pw2").val("").focus();
				 return false;
			 }
		 }
		 function execDaumPostcode() {
			 new kakao.Postcode({
				 oncomplete: function(data) {
					 $("#m_addr").val(data.roadAddress);
					 // 상세주소는 직접 입력
				 }
			 }).open();
		 }

         function sendVerificationCode() {
			 const email = $("#m_email").val().trim();
			 // 만들때 (정규식 공부하기 )
			 // if(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(email)){
				//  alert("이메일 형식이 올바르지 않습니다.")
				//  return false;
			 // }
			 if(!/^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$/.test(email)){
				 alert("이메일 형식이 올바르지 않습니다.");
				 return;
			 }
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
		 }
		 function checkVerificationCode() {
			 const code = $("#emailCode").val().trim();
			 if(code === ""){
				 alert("인증코드 입력하세요");
				 $("#emailCode").focus();
				 return;
			 }
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
		             if(data === "match"){
						 $("#m_phone").prop("disabled", false);
						 $("#submitBtn").prop("disabled", false);
						 $("#result").css("color", "green");
					 }else{
						 $("#m_phone").prop("disabled", true);
						 $("#submitBtn").prop("disabled", true);
						 $("#result").css("color", "red");
					 }

				 },
				 error: function() {
					 alert("인증실패");
				 }
			 });
		 }
	</script>
</body>
</html>