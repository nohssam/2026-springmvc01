<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--  파일 업로드할때는 반드시 form 안에  post, enctype="multipart/form-data"--%>
<form method="post" enctype="multipart/form-data"
      action="${pageContext.request.contextPath}/fileup/uploadok">
    <table>
        <tbody>
            <tr>
                <th>파일 선택</th>
                <td><input type="file" name="file_name" id="fileInput" required> </td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="2">
                    <input type="submit" value="업로드">
                </td>
            </tr>
        </tfoot>
    </table>
</form>
<script>
    // 빠름, 서버 부하 없음, 브라우저에 저장
     document.querySelector("#fileInput").addEventListener("change", (e) => {
         const file = e.target.files[0];
         const reader = new FileReader();
         reader.onload = (e) => {
             // 브라우저가 가지고 있는 sessionStorage에 저장하자 (key,value)
             sessionStorage.setItem('previewData', e.target.result);
         };
         // base64 문자열로 변환
         reader.readAsDataURL(file);
     })
</script>
</body>
</html>
