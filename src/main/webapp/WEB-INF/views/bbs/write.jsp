<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
  <form method="post" encType="multipart/form-data"  action="${pageContext.request.contextPath}/bbs/writeok">
    <table>
      <caption>게시판 글쓰기</caption>
      <tbody>
      <tr>
        <th>제목:</th>
        <td><input type="text" name="subject" size="45" required></td>
      </tr>
      <tr>
        <th>이름:</th>
        <td><input type="text" name="writer" size="12" required></td>
      </tr>
      <tr>
        <th>내용:</th>
        <td><textarea name="content" cols="50" rows="8" required></textarea></td>
      </tr>
      <tr>
        <th>첨부파일:</th>
        <td><input type="file" name="file_name"></td>
      </tr>
      <tr>
        <th>비밀번호:</th>
        <td><input type="password" name="pwd" size="12" required></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center;">
          <input type="submit" value="보내기">
          <input type="reset" value="다시">
          <input type="button" value="목록" onclick="bbs_list(this.form)">
        </td>
      </tr>
      </tbody>
    </table>
  </form>
</div>
<script>
   function bbs_list(f) {
     f.action = "${pageContext.request.contextPath}/bbs/list";
     f.submit();
  }
</script>
</body>
</html>

