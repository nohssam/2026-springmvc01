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
    <h2>방명록:수정화면</h2>
    <hr>
    <p> [ <a href="${pageContext.request.contextPath}/guestbook/list3">목록으로 이동 </a>]</p>
    <form method="post"
          action="${pageContext.request.contextPath}/guestbook/updateok"
          enctype="multipart/form-data"  >
      <table>
        <tbody>
          <tr>
             <th class="skyblue">작성자</th>
             <td><input type="text" name="g_writer" value="${guestBookVO.g_writer}" required></td>
          </tr>
          <tr>
            <th class="skyblue">제  목</th>
            <td><input type="text" name="g_subject" value="${guestBookVO.g_subject}" required></td>
          </tr>
          <tr>
            <th class="skyblue">이메일</th>
            <td><input type="text" name="g_email" value="${guestBookVO.g_email}" required></td>
          </tr>
          <tr>
            <th class="skyblue">첨부파일</th>
            <td>
                <c:choose>
                  <c:when test="${not empty guestBookVO.f_name}">
                    <input type="file" name="file_name"> <br><b>이전파일(${guestBookVO.f_name})</b>
                  </c:when>
                  <c:otherwise>
                    <input type="file" name="file_name"> <br><b>이전파일없음</b>
                  </c:otherwise>
                </c:choose>

            </td>
          </tr>
          <tr>
            <th class="skyblue">비밀번호</th>
            <td><input type="password" name="g_pwd" required></td>
          </tr>
          <tr>
            <td colspan="2">
              <textarea name="g_content" rows="10" cols="50" required> ${guestBookVO.g_content}</textarea>
            </td>
          </tr>
        </tbody>
        <tfoot>
            <tr>
              <td colspan="2">
                   <input type="hidden" name="g_idx" value="${guestBookVO.g_idx}">
                   <input type="button" value="수정하기" onclick="update_go(this.form)">
                   <input type="reset" value="취소하기">
              </td>
            </tr>
        </tfoot>
      </table>
    </form>
  </div>
  <script>
    <c:if test="${pwdchk == 'fail'}">
      alert("비밀번호 틀림");
    </c:if>

    function update_go(f){
      f.submit();
    }
  </script>
</body>
</html>
