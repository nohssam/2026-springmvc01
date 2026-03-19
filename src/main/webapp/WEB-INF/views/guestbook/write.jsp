<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <h2>방명록:작성화면</h2>
    <hr>
    <p> [ <a href="${pageContext.request.contextPath}/guestbook/list3">목록으로 이동 </a>]</p>
    <form method="post"
          action="${pageContext.request.contextPath}/guestbook/writeok"
          enctype="multipart/form-data">
      <table>
        <tbody>
          <tr>
             <th class="skyblue">작성자</th>
             <td><input type="text" name="g_writer" required></td>
          </tr>
          <tr>
            <th class="skyblue">제  목</th>
            <td><input type="text" name="g_subject" required></td>
          </tr>
          <tr>
            <th class="skyblue">이메일</th>
            <td><input type="text" name="g_email" required></td>
          </tr>
          <tr>
            <th class="skyblue">비밀번호</th>
            <td><input type="password" name="g_pwd" required></td>
          </tr>
          <tr>
            <th class="skyblue">첨부파일</th>
            <td><input type="file" name="file_name"></td>
          </tr>
          <tr>
            <td colspan="2">
              <textarea name="g_content" rows="10" cols="50" required></textarea>
            </td>
          </tr>
        </tbody>
        <tfoot>
            <tr>
              <td colspan="2">
                   <input type="submit" value="저장하기">
                   <input type="reset" value="취소하기">
              </td>
            </tr>
        </tfoot>
      </table>
    </form>
  </div>
</body>
</html>
