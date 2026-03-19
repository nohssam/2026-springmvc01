<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>업로드 완료</h2>
    <hr>
    <ul>
      <li>원본이름 : ${ori_name} </li>
      <li>저장이름 : ${savename} </li>
      <li>파일크기 : ${fileSize} </li>
      <li>파일타입 : ${contentType}  </li>
    </ul>
    <a href="${pageContext.request.contextPath}/fileup/download?savename=${savename}&ori_name=${ori_name}">
     <img  src="${pageContext.request.contextPath}/upload2/${savename}" alt="" style="width: 100px">
    </a>
</body>
</html>
