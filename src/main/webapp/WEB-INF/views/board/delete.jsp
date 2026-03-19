<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <form method="post" action="${pageContext.request.contextPath}/board/deleteok">
        <table>
            <caption>Board 글 삭제</caption>
            <tbody>
            <tr>
                <th class="title" style="width: 40%">비밀번호확인 : </th>
                <td><input type="password" name="pwd" required></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="hidden" name="b_idx" value="${b_idx}">
                    <input type="hidden" name="nowPage" value="${nowPage}">
                    <input type="submit" value="삭제" >
                    <input type="button" value="목록" onclick="board_list()">
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<script>
    function board_list() {
        location.href = "${pageContext.request.contextPath}/board/list?nowPage=${nowPage}";
    }
</script>
</body>
</html>

