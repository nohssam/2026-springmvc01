<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
 <h2> Index.JSP 페이지 입니다.</h2>
 <p><button onclick="send_go()">오늘의 운세</button></p>
<%-- 파일 업로드 하기 위해서는 업로드 위치를 지정해야 된다. (application.properties) --%>
 <p> <a href="${pageContext.request.contextPath}/fileup/fileupForm">파일 업로드 화면으로 이동</a></p>

 <%-- 파일 업로드 하기 위해서는 업로드 위치를 지정해야 된다. (application.properties) --%>
 <p><a href="${pageContext.request.contextPath}/fileup/upload2">파일 업로드 화면으로 이동2</a></p>
 <p><a href="${pageContext.request.contextPath}/guestbook/list">GuestBook</a></p>
 <p><button onclick="send_bbs()">게시판1(BBS)</button></p>
 <p><button onclick="send_board()">게시판2(Board)</button><p>
 <p><button onclick="send_shop()">쇼핑몰</button><p>
 <p><button onclick="send_addr()">다음 주소록</button><p>
 <p><button onclick="send_maps()">kakao map</button><p>
 <p><button onclick="send_email()">email 검증</button><p>
<script>
    function send_go(){
        location.href=`${pageContext.request.contextPath}/guestbook/luck`;
    }
    function send_bbs() {
       location.href=`${pageContext.request.contextPath}/bbs/list`;
    }
    function send_board() {
     location.href=`${pageContext.request.contextPath}/board/list`;
    }
    function send_shop() {
     location.href=`${pageContext.request.contextPath}/shop/list`;
    }
    function send_addr() {
     location.href=`${pageContext.request.contextPath}/daum/addrForm`;
    }
    function send_maps() {
     location.href=`${pageContext.request.contextPath}/daum/kakaomapindex`;
    }
    function send_email() {
     location.href=`${pageContext.request.contextPath}/email/emailForm`;
    }
</script>
</body>
</html>
