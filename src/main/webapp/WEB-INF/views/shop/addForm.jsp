<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table {
	margin: 10px auto;
	width: 600px;
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
.content{text-align: left; padding-left: 50px}
</style>
</head>
<body>
<jsp:include page="top.jsp" />
	<hr>
   <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/shop/addShop">
	<table>
		<tbody>
		<tr>
			<td style="width: 25%">제품 Category</td>
			<td style="width: 75%" class="content">
				<label> <input type="radio" name="category" value="com001" checked > 컴퓨터 </label>
				<label> <input type="radio" name="category" value="ele002"  > 가전 제품 </label>
				<label> <input type="radio" name="category" value="sp003"  > 스포츠 </label>
			</td>
		</tr>
		<tr>
			<td>제품번호</td>
			<td class="content"><input type="text" name="p_num" required></td>
		</tr>
		<tr>
			<td>제품이름</td>
			<td class="content"><input type="text" name="p_name" required></td>
		</tr>
		<tr>
			<td>제품판매사</td>
			<td class="content"><input type="text" name="p_company" required></td>
		</tr>
		<tr>
			<td>제품가격(정가)</td>
			<td class="content"><input type="number" name="p_price" min="1000" required></td>
		</tr>
		<tr>
			<td>제품가격(할인가)</td>
			<td class="content"><input type="number" name="p_saleprice" min="1000" required></td>
		</tr>
		<tr>
			<td>상품이미지_s</td>
			<td class="content"><input type="file" name="file_s" required></td>
		</tr>
		<tr>
			<td>상품이미지_l</td>
			<td class="content"><input type="file" name="file_l" required></td>
		</tr>
		<tr>
			<td>제품설명</td>
			<td class="content"><textarea rows="5" cols="50" name="p_content" required></textarea></td>
		</tr>
		</tbody>
		<tfoot>
		   <tr><td colspan="2"><input type="submit" value="상품등록"></td></tr>
		</tfoot>
	</table>
   </form>
</body>
</html>