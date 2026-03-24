<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>결과보기</h3>
    <ul>
        <li>우편번호 : ${addrVO.postcode} </li>
        <li>도로명주소 : ${addrVO.roadAddress} </li>
        <li>지번주소 : ${addrVO.jubunAddress} </li>
        <li>상세주소 : ${addrVO.detailAddress} </li>
        <li>참고항목 :${addrVO.extraAddress}  </li>
    </ul>
</body>
</html>
