<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	margin: 10px auto;
	width: 800px;
	border-collapse: collapse;
	font-size: 12pt;
	border-color: navy;
}

table, th, td {
	border: 1px solid black;
}
td{
	padding: 10px;
}
img{
	width: 450px;
}
</style>
</head>
<body>
<jsp:include page="top.jsp" />
	<hr>
	<table>
		<tr>
			<td style="width: 25%">제품Category</td>
			<td style="width: 75%">${svo.category}</td>
		</tr>
		<tr>
			<td>제품번호</td>
			<td>${svo.p_num}</td>
		</tr>
		<tr>
			<td>제품이름</td>
			<td>${svo.p_name}</td>
		</tr>
		<tr>
			<td>제품판매사</td>
			<td>${svo.p_company}</td>
		</tr>
		<tr>
			<td>제품가격</td>
			<td>정가: <fmt:formatNumber value="${svo.p_price}" pattern="#,###"/> 원<br>
				<span style="color: red">(할인가: <fmt:formatNumber value="${svo.p_saleprice}" pattern="#,###"/> 원)</span>
			</td>
		</tr>
		<tr>
			<td>제품설명</td>
			<td>${svo.p_content}</td>
		</tr>
		<tr>
            <td colspan="2"><img src='<c:url value="/resources/images/${svo.p_image_l}" />'> </td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center">
				<input type="button" value="장바구니 담기" onclick="addCartGo()" >
				<input type="button" value="장바구니 보기"	onclick="ShowCartGo()" >
			</td>
		</tr>
	</table>
	<script type="text/javascript">
       <%--	로그인이 되어있어야 한다.제대로 넘어간다.(로그인 Spring에서 한다.)--%>
		function addCartGo() {
			// 제품 정보를 담기 위해서 shop_idx를 가져가자
              location.href = "${pageContext.request.contextPath}/shop/addCart?shop_idx=${svo.shop_idx}";
		}
		function ShowCartGo() {
			location.href = "${pageContext.request.contextPath}/shop/showCart";
		}
	</script>
</body>
</html>