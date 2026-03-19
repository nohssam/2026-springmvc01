<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#container{
		width: 500px;
		margin: 100px auto;   /* 중앙위치 */
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
    #buttons{
    	text-align: center;
    	margin: 20px 0px;
    }
    input[type="submit"],
    input[type="reset"]{
    	width: 100px;
    }
    a{text-decoration: none;}
    span {margin-left: 20px;}
    
</style>
</head>
<body>
    <%-- <jsp:include page=""></jsp:include> --%>
	<%@ include file="../shop/top.jsp" %>
	<div id="container">
		<h2>일반 로그인</h2>
		<div>
			<form action="${pageContext.request.contextPath}/member/loginok" method="post">
				<fieldset>
					<legend>로그인</legend>
					<ul>
						<li><label for="m_id">ID: </label> 
						    <input type="text" id="m_id" name="m_id" required>
						</li>
						<li><label for="m_pw">PW: </label> 
						    <input type="password" id="m_pw" name="m_pw" required>
						</li>
					</ul>
					<div id="buttons">
						<input type="submit" value="로그인">
						<input type="reset" value="취소">
					</div>
					<div> 회원 가입 하시겠습니까? <span><a href="/membersJoinForm">회원가입</a></span>
					</div>
				</fieldset>
			</form>
			<div id="sns_login">
			<p>SNS 계정으로 로그인</p>
				<img style="width: 150px" src='<c:url value="/resources/images/kakao_login_large_narrow.png" />'>
				<img width="160px;" src='<c:url value="/resources/images/btnG_logIn.png" />'>
			</div>
		</div>
	</div>	
	<c:if test="${logInChk == 'fail' }">
		<script type="text/javascript">
			alert("아이디나 비밀번호가 틀렸습니다.")
		</script>
	</c:if>
	<c:if test="${sessionScope.logInChk == 'required' }">
		<script type="text/javascript">
			alert("로그인 후 사용 가능")
		</script>
	</c:if>
</body>
</html>