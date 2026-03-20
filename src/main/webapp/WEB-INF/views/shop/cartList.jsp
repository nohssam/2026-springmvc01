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
td{
	padding: 10px 0px;
}

caption {
	font-size: 24px;
}
</style>
</head>
<body>
<jsp:include page="top.jsp" />
	<hr>
	<table>
		<caption> :: 장바구니 내용 </caption>
		<thead>
			<tr style="background-color: #dedede">
				<th>제품번호</th>
				<th>제품명</th>
				<th>단가</th>
				<th>수량</th>
				<th>금액</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${empty cartList}">
				<tr><td colspan="6"><h3>장바구니가 비었습니다</h3></td></tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="k" items="${cartList}">
					<tr>
						<td>${k.p_num}</td>
						<td>${k.p_name}</td>
						<td>정가 : <fmt:formatNumber value="${k.p_price}" pattern="#,##0" /> 원 <br>
							<span style="color: red">
							  (할인가 :	<fmt:formatNumber value="${k.p_saleprice}" pattern="#,##0" /> ) 원
							</span>
						</td>
						<td>
							<form action="${pageContext.request.contextPath}/shop/editCart" method="post">
								<input type="number" name="p_su" value="${k.p_su}" min="1" style="width: 30px"/>
								<input type="hidden" name="p_num" value="${k.p_num}" />
								<input type="hidden" name="m_id" value="${k.m_id}" />
								<input type="submit" value="수정">
							</form>
						</td>
						<td><fmt:formatNumber value="${k.p_saleprice * k.p_su}" pattern="#,##0" /> 원 </td>
						<td>
							<form action="${pageContext.request.contextPath}/shop/deleteCart" method="post">
								<input type="hidden" name="p_num" value="${k.p_num}" />
								<input type="hidden" name="m_id" value="${k.m_id}" />
								<input type="submit" value="삭제">
							</form>
						</td>
					</tr>
					<%-- 누적합 구하기 --%>
					<c:set var="cartTotal" value="${cartTotal = cartTotal + (k.p_saleprice * k.p_su)}" />
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</tbody>
		<tfoot>
             <tr>
				 <c:choose>
					 <c:when test="${empty cartList}">
						 <td colspan="6" style="text-align: right; padding-right: 30px" >총 결재액 : 0 원 </td>
					 </c:when>
					 <c:otherwise>
						 <td colspan="6" style="text-align: right; padding-right: 30px" >총 결재액 :
							 <fmt:formatNumber value="${cartTotal}" pattern="#,##0" /> 원
						 </td>
					 </c:otherwise>
				 </c:choose>
			 </tr>
		</tfoot>
	</table>

</body>
</html>