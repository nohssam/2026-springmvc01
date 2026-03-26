<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
 a:link{text-decoration:none; color:navy}
 a:visited{text-decoration:none; color:navy}
 a:hover{text-decoration:none; color:red}
 body{ text-align: center } 
 hr{
	width: 600px;
	border: 1px;
	color: navy;
 }
 div#header, div#nav{
	width: 600px;
	margin: 10px auto;
	text-align: center;
 }
 div#wrap{ margin: 0 auto; }
 div#log{float:right;} 
  .title{font-size: 32px;}
</style>
  </head>
  <body>
  <div id="wrap">
	  <div id="header">
		  <span class="title"> ICT SHOPPING CENTER  </span>
	  </div>
	  <div id="nav">
		  <a href="${pageContext.request.contextPath}/shop/list?category=com001">컴퓨터</a> |
		  <a href="${pageContext.request.contextPath}/shop/list?category=ele002">가전 제품</a> |
		  <a href="${pageContext.request.contextPath}/shop/list?category=sp003">스포츠</a> |
		  <div id="log">
			<%--	로그인 여부 따라 보이는 부분이 달라진다.
					 로그인 여부 정보 session에 담는다.
					 시간을 별도로 정하지 않으면  브라우저가 끝날때 session 정보도 사라진다.
					 로그인 성공하면 session에 logInChk 이름, ok 값 저장되어있다.--%>
			    <c:choose>
			       <c:when test="${logInChk == 'ok'}">
				      ${mvo.m_name}님 환영합니다. |
				      <c:if test="${mvo.m_id == 'admin'}">
				      	<a href="${pageContext.request.contextPath}/shop/addForm">상품 등록 페이지</a> |
				      </c:if>
				      <c:if test="${mvo.m_id != 'admin'}">
				      	<a href="${pageContext.request.contextPath}/shop/showCart">장바구니</a> |
				      </c:if>
				      <a href="${pageContext.request.contextPath}/member/logout">로그아웃</a> |
				   </c:when>
			       <c:otherwise>
                        <a href="${pageContext.request.contextPath}/member/loginForm">로그인 </a> |
					    <a href="${pageContext.request.contextPath}/member/joinForm">회원가입 </a> |
					    <a href="${pageContext.request.contextPath}/shop/showCart">장바구니</a>
                   </c:otherwise>
				</c:choose>
		  </div>
	  </div>
  </div>
  </body>
</html>








