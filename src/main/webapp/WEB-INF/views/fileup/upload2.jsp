<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--  파일 업로드할때는 반드시 form 안에  post, enctype="multipart/form-data"--%>
<form method="post" enctype="multipart/form-data"
      action="${pageContext.request.contextPath}/fileup/uploadok2">
    <table>
        <tbody>
            <tr>
                <th>파일 선택</th>
                <td><input type="file" name="file_name"  required> </td>
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
</body>
</html>
