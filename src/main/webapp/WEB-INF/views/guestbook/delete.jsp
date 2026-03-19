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
    <h2>방명록:삭제화면</h2>
    <hr>
    <p> [ <a href="${pageContext.request.contextPath}/guestbook/list3">목록으로 이동 </a>]</p>
    <form method="post" >
      <table>
        <tbody>
          <tr>
             <th class="skyblue">비밀번호</th>
             <td><input type="password" name="g_pwd" required></td>
          </tr>
        </tbody>
        <tfoot>
            <tr>
              <td colspan="2">
                   <input type="hidden" name="g_idx" value="${g_idx}">
                   <input type="button" value="삭제하기" onclick="delete_go(this.form)">
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

  function delete_go(f){
    f.action="${pageContext.request.contextPath}/guestbook/deleteok";
    f.submit();
  }
</script>
</body>
</html>
