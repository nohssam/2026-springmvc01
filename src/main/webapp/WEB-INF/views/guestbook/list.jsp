<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        table{
            width: 600px;
            margin: 0 auto;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
            text-align: center;
            padding: 3px;
        }
        div{
            width: 600px;
            text-align: center;
            margin: 0 auto;
        }
        .skyblue{background-color: skyblue}
    </style>
</head>
<body>
<div>
    <h2>방명록 리스트</h2>
    <p> [ <a href="${pageContext.request.contextPath}/guestbook/write">방명록 쓰기</a>  ]</p>
    <table>
        <thead>
            <tr>
                <td class="skyblue">번호</td>
                <td class="skyblue">작성자</td>
                <td class="skyblue">제 목</td>
                <td class="skyblue">작성일</td>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty list}">
                    <tr><td colspan="4"><h3></h3>원하는 정보가 존재하지 않습니다</td></tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="k" items="${list}" varStatus="s">
                        <tr>
<%--                            <td>${k.g_idx}</td>--%>
                            <td>${s.count}</td>
                            <td>${k.g_writer}</td>
                            <td><a href="${pageContext.request.contextPath}/guestbook/detail?g_idx=${k.g_idx}">${k.g_subject}</a></td>
                            <td>${k.g_regdate}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</div>
<script>
    // SP가 URL 쿼리스트링을 직접 읽는 내장 객체
    // 컨트롤러를 거치지 않고 JSP가 URL에서 직접 꺼내 쓰는 방식입니다.
  <c:if test="${param.msg == 'deleted'}">
    alert("삭제되었습니다.");
  </c:if>
</script>
</body>
</html>
