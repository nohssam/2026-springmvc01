<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
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
img{
	width: 100px;
}
</style>
</head>
<body>
    <%--  현재 페이지에서 다른 가져오는 것 : include  --%>
    <%--	<%@ include file="top.jsp"%>--%>
	<jsp:include page="top.jsp" />
	<hr>
	<table>
		<thead>
			<tr style="background-color: #dedede">
				<th style="width: 10%">제품번호</th>
				<th style="width: 10%">이미지</th>
				<th style="width: 25%">제품명</th>
				<th style="width: 20%">제품가격</th>
				<th style="width: 25%">비고</th>
			</tr>
		</thead>
		<tbody>
           <c:choose>
			   <c:when test="${empty list}">
				   <tr><td colspan="5"><h3>제품 준비중 입니다</h3> </td></tr>
			   </c:when>
			   <c:otherwise>
				   <c:forEach var="k" items="${list}" >
					   <tr>
						   <td>${k.p_num}</td>
							<%-- 이미지가  webapp/resources/images  --%>
						   <td><img src='<c:url value="/resources/images/${k.p_image_s}" />'></td>
						   <td><a href="${pageContext.request.contextPath}/shop/detail?shop_idx=${k.shop_idx}">${k.p_name}</a></td>
						   <td>할인가 : <fmt:formatNumber value="${k.p_saleprice}" pattern="#,##0" /> 원<br>
						       <span style="color: red">(할인률: ${k.getPercent()}%)</span></td>
						   <td>정가 : <fmt:formatNumber value="${k.p_price}" pattern="#,##0" /> 원</td>
					   </tr>
				   </c:forEach>
			   </c:otherwise>
		   </c:choose>
		</tbody>
	</table>
</body>
</html>