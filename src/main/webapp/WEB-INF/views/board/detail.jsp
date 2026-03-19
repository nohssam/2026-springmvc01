<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <style>
    #bbs table {
      width: 580px;
      margin: 0 auto;
      margin-top: 20px;
      border: 1px solid black;
      border-collapse: collapse;
      font-size: 14px;
    }
    #bbs table caption {
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 10px;
    }
    #bbs table th, #bbs table td {
      text-align: center;
      border: 1px solid black;
      padding: 4px 10px;
    }
    .title { background: lightsteelblue }
  </style>

</head>
<body>
<div id="bbs">
  <form method="post">
    <table>
      <caption>Board Detail</caption>
      <tbody>
      <tr>
        <th class="title" style="width: 20%">제목:</th>
        <td style="text-align: left">${bvo.title}</td>
      </tr>
      <tr>
        <th class="title">이름:</th>
        <td style="text-align: left">${bvo.writer}</td>
      </tr>
      <tr>
        <th class="title">내용:</th>
        <td style="text-align: left"><textarea name="content" cols="50" rows="8" disabled>${bvo.content}</textarea></td>
      </tr>
      <tr>
        <th class="title">첨부파일:</th>
        <td style="text-align: left">
          <c:choose>
            <c:when test="${not empty bvo.f_name}">
              <a href="${pageContext.request.contextPath}/board/download?f_name=${bvo.f_name}">
                <img style="width: 150px" src="${pageContext.request.contextPath}/upload2/${bvo.f_name}" alt="">
              </a>
            </c:when>
            <c:otherwise> 첨부파일 없음</c:otherwise>
          </c:choose>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="hidden" name="b_idx" value="${bvo.b_idx}">
          <input type="hidden" name="nowPage" value="${nowPage}">
          <input type="button" value="목록" onclick="board_list(this.form)">
          <input type="button" value="수정" onclick="board_updateForm(this.form)">
          <input type="button" value="삭제" onclick="board_deleteForm(this.form)">
          <input type="button" value="답글" onclick="board_AnsWriteForm(this.form)">
        </td>
      </tr>
      </tbody>
    </table>
  </form>
</div>
<script>
  <c:if test="${not empty pwd_error}">
   window.onload = function() { alert("비밀번호 틀림"); true}
  </c:if>
  function board_list(f) {
    f.action="${pageContext.request.contextPath}/board/list";
    f.submit();
  }
  function board_updateForm(f) {
    f.action="${pageContext.request.contextPath}/board/updateForm";
    f.submit();
  }
  function board_deleteForm(f) {
    f.action="${pageContext.request.contextPath}/board/deleteForm";
    f.submit();
  }
  function board_AnsWriteForm(f) {
    f.action="${pageContext.request.contextPath}/board/AnsWriteForm";
    f.submit();
  }
</script>
</body>
</html>

