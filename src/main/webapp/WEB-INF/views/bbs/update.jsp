<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <style type="text/css">
    #bbs table {
      width:580px;
      margin:0 auto;
      margin-top:20px;
      border:1px solid black;
      border-collapse:collapse;
      font-size:14px;

    }

    #bbs table caption {
      font-size:20px;
      font-weight:bold;
      margin-bottom:10px;
    }

    #bbs table th {
      text-align:center;
      border:1px solid black;
      padding:4px 10px;
    }

    #bbs table td {
      text-align:left;
      border:1px solid black;
      padding:4px 10px;
    }

    .no {width:15%}
    .subject {width:30%}
    .writer {width:20%}
    .reg {width:20%}
    .hit {width:15%}
    .title{background:lightsteelblue}
    .odd {background:silver}
  </style>

</head>
<body>
<div id="bbs">
  <form method="post" encType="multipart/form-data"
        action="${pageContext.request.contextPath}/bbs/updateok">
    <table>
      <caption>게시판 글 수정</caption>
      <tbody>
      <tr>
        <th class="title" style="width: 20%">제목:</th>
        <td><input type="text" name="subject" size="45" value="${bvo.subject}" required></td>
      </tr>
      <tr>
        <th>이름:</th>
        <td><input type="text" name="writer" size="12" value="${bvo.writer}" required></td>
      </tr>
      <tr>
        <th>내용:</th>
        <td><textarea name="content" cols="50" rows="8" required>${bvo.content}</textarea></td>
      </tr>
      <tr>
        <th class="title">첨부파일:</th>
        <td style="text-align: left">
         <c:if test="${not empty bvo.f_name}">
            <div> 현재 파일 :
              <a href="${pageContext.request.contextPath}/bbs/download?f_name=${bvo.f_name}">
                 <img style="width:80px;" src="${pageContext.request.contextPath}/upload2/${bvo.f_name}" alt="${bvo.f_name}">
              </a>
            </div>
         </c:if>
          <input type="file" name="file_name">
          <small>(새 파일을 선택하면 기존 파일이 교체 됩니다)</small>
        </td>
      </tr>
      <tr>
        <th>비밀번호:</th>
        <td><input type="password" name="pwd" size="12" required></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center;">
          <input type="hidden" name="b_idx" value="${bvo.b_idx}">
          <input type="hidden" name="nowPage" value="${nowPage}">
          <input type="submit" value="수정완료">
          <input type="button" value="목록" onclick="bbs_list()">
        </td>
      </tr>
      </tbody>
    </table>
  </form>
</div>
<script>
  function bbs_list() {
       location.href = "${pageContext.request.contextPath}/bbs/list?nowPage=${nowPage}";
   }
</script>
</body>
</html>

