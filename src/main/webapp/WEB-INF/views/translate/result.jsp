<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>번역 결과</title>
    <style>
        body {
            font-family: 'Malgun Gothic', sans-serif;
            max-width: 700px;
            margin: 40px auto;
            padding: 0 20px;
            background: #f5f7fa;
        }
        h2 {
            color: #2c3e50;
            border-bottom: 2px solid #27ae60;
            padding-bottom: 8px;
        }
        /* 원문/번역문 표시 박스 */
        .text-box {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 14px 16px;
            margin-bottom: 16px;
            white-space: pre-wrap;   /* 원문의 줄바꿈 유지 */
            word-break: break-word;
            line-height: 1.7;
        }
        .text-box b {
            display: block;
            margin-bottom: 6px;
            color: #555;
            font-size: 13px;
        }
        /* 에러 메시지 */
        .error-msg {
            color: #e74c3c;
            background: #fdecea;
            border: 1px solid #e74c3c;
            border-radius: 6px;
            padding: 12px 16px;
            margin-bottom: 16px;
        }
        /* 언어 정보 */
        .lang-info {
            color: #777;
            font-size: 13px;
            margin-bottom: 16px;
        }
        /* 다시 번역하기 링크 버튼 */
        .btn-back {
            display: inline-block;
            padding: 9px 22px;
            background: #27ae60;
            color: #fff;
            text-decoration: none;
            border-radius: 6px;
            font-size: 14px;
        }
        .btn-back:hover {
            background: #1e8449;
        }
    </style>
</head>
<body>

<h2>번역 결과</h2>

<%-- 에러 발생 시 에러 메시지 표시 --%>
<c:if test="${not empty error}">
    <div class="error-msg">
        <strong>오류 발생:</strong> <c:out value="${error}"/>
    </div>
</c:if>

<%-- 번역 성공 시 원문 + 번역문 표시 --%>
<c:if test="${empty error}">

    <%-- 원문 표시 --%>
    <div class="text-box">
        <b>원문</b>
        <c:out value="${original}"/>
    </div>

    <%-- 번역문 표시 --%>
    <div class="text-box">
        <b>번역 (한국어)</b>
        <c:out value="${translated}"/>
    </div>

    <%-- 소스 언어 → 타깃 언어 정보 --%>
    <p class="lang-info">
        소스: <strong>${empty source ? '자동 감지' : source}</strong>
        &nbsp;→&nbsp;
        타깃: <strong>${target}</strong>
    </p>

</c:if>

<%-- 번역 폼으로 돌아가기 --%>
<a href="${pageContext.request.contextPath}/translate" class="btn-back">다시 번역하기</a>

</body>
</html>
