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
    <h2>방명록:내용화면</h2>
    <hr>
    <p> [ <a href="${pageContext.request.contextPath}/guestbook/list3">목록으로 이동 </a>]</p>
    <form method="post">
      <table>
        <tbody>
          <tr>
             <th class="skyblue">작성자</th>
             <td>${guestBookVO.g_writer}</td>
          </tr>
          <tr>
            <th class="skyblue">제  목</th>
            <td>${guestBookVO.g_subject}</td>
          </tr>
          <tr>
            <th class="skyblue">이메일</th>
            <td>${guestBookVO.g_email}</td>
          </tr>
          <tr>
              <th class="skyblue">첨부파일</th>
              <td>
                  <c:choose>
                      <c:when test="${not empty guestBookVO.f_name}">
                          <a href="${pageContext.request.contextPath}/guestbook/downlaod?f_name=${guestBookVO.f_name}">
                              <img src="${pageContext.request.contextPath}/upload2/${guestBookVO.f_name}" alt="" style="width:150px;">
                          </a>
                      </c:when>
                      <c:otherwise>첨부파일없음</c:otherwise>
                  </c:choose>
              </td>
          </tr>
          <tr>
            <td colspan="2" >
<%--              <pre style="text-align: left; padding-left: 30px">${guestBookVO.g_content}</pre>--%>
                 <textarea name="g_content" rows="10" cols="50" disabled>${guestBookVO.g_content}</textarea>
            </td>
          </tr>
        </tbody>
        <tfoot>
            <tr>
              <td colspan="2">
                   <input type="hidden" name="g_idx" value="${guestBookVO.g_idx}" >
                   <%--  비밀번호 암호화를 사용해서 필요 없어짐
                   <input type="hidden" name="g_pwd" value="${guestBookVO.g_pwd}" >
                   --%>
                   <input type="button" value="삭제하기" onclick="delete_go(this.form)">
                   <input type="button" value="수정하기" onclick="update_go(this.form)">
              </td>
            </tr>
        </tfoot>
      </table>
    </form>
  </div>
  <script>
    function delete_go(f){
      f.action =`${pageContext.request.contextPath}/guestbook/deleteForm`;
      f.submit();
    }
    function update_go(f) {
      f.action ="${pageContext.request.contextPath}/guestbook/updateForm";
      f.submit();
    }
  </script>
</body>
</html>
