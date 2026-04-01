<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <style>
    button {margin: 3px; padding: 5px;}
    table{border-collapse: collapse; }
    th,td{border: 1px solid #ddd; text-align: center; padding: 5px;}
    th{background-color: #ddd;}
  </style>
</head>
<body>
  <h2> 서울시 공공 데이터</h2>
  <div>
    <button id="btn1">서울시 문화 행사 정보(XML)</button>
    <button id="btn2">자랑스러운 한국 음식점(JSON)</button>
  </div>
  <hr>
<%--  결과 보여주는 영역--%>
  <div id="result"></div>

  <script src="https://code.jquery.com/jquery-4.0.0.js"></script>
  <script>
      $("#btn1").on("click", function() {
          $("#result").html("<p>불러오는 중...</p>");
          $.ajax({
            url: "${pageContext.request.contextPath}/cultural" , // 가고자 하는 서버 주소 (endPoint)
            method: "GET",      // 전달방식 ; GET, POST
            // data : "",    // 서버에 갈때 가지고가는 파라미터 값
            dataType: "JSON",    // 서버에서 값을 가지고 올때 데이터의 자료형
            success: function (data) {
              // console.log(data);
              if(!data || data.length == 0) {
                $("#result").html("<p>데이터가 없습니다.</p>");
              }

              let table = "<table>";
              table += "<thead><tr>";
              table += "<th>분류</th><th>자치구</th><th>행사명</th><th>장소</th>";
              table += "<th>시작일</th><th>종료일</th><th>금액</th><th>유무료</th>";
              table += "</tr></thead>";
              table += "<tbody>";
              $.each(data, function (i, vo) {
                table += "<tr>";
                table += "<td>" + (vo.CODENAME || "-")  + "</td>";
                table += "<td>" + (vo.GUNAME || "-")  + "</td>";
                table += "<td>" + (vo.TITLE || "-")  + "</td>";
                table += "<td>" + (vo.PLACE || "-")  + "</td>";
                table += "<td>" + (vo.STRTDATE.substring(0,10) || "-")  + "</td>";
                table += "<td>" + (vo.END_DATE.substring(0,10) || "-")  + "</td>";
                table += "<td>" + (vo.USE_FEE || "-")  + "</td>";
                table += "<td>" + (vo.IS_FREE || "-")  + "</td>";
                table += "</tr>";
              });
              table += "</tbody>";
              table += "</table>";
               $("#result").html("<p>총 <strong>" + data.length + "</strong>건</p>" + table);
            },
            error: function () {
               $("#result").html("<p style='color:red'>문화행사 로드 실패</p>");
            }

          });
      });

      $("#btn2").on("click", function() {
        $("#result").html("<p>불러오는 중...</p>");
          $.ajax({
            url: "${pageContext.request.contextPath}/KorRes" , // 가고자 하는 서버 주소 (endPoint)
            method: "GET",      // 전달방식 ; GET, POST
            // data : "",    // 서버에 갈때 가지고가는 파라미터 값
            dataType: "json",    // 서버에서 값을 가지고 올때 데이터의 자료형
            success: function (data) {
                // console.log(data);
              if(!data || data.length == 0) {
                $("#result").html("<p>데이터가 없습니다.</p>");
              }
              let table = "<table>";
              table += "<thead><tr>";
              table += "<th>대분류</th><th>중분류</th><th>소분류</th>"
              table += "<th>상호명</th><th>한국어명</th><th>시/도</th><th>구</th><th>동</th>"
              table += "</tr></thead>";
              table += "<tbody>";
              $.each(data, function (i, vo) {
                table += "<tr>";
                table += "<td>" +(vo.CATE1_NAME || "-")+"</td>";
                table += "<td>" +(vo.CATE2_NAME || "-")+"</td>";
                table += "<td>" +(vo.CATE3_NAME || "-")+"</td>";
                table += "<td>" +(vo.NM_DP || "-")+"</td>";
                table += "<td>" +(vo.NAME_KOR || "-")+"</td>";
                table += "<td>" +(vo.H_KOR_CITY || "-")+"</td>";
                table += "<td>" +(vo.H_KOR_GU || "-")+"</td>";
                table += "<td>" +(vo.H_KOR_DONG || "-")+"</td>";
                table += "<tr>";
              });
              table += "</tbody></table>";
              $("#result").html("<p>총 <strong>" + data.length + "</strong>건</p>" + table);

            },
            error: function () {
              $("#result").html("<p style='color:red'>한국 음식점 로드 실패</p>");
            }

          });
      });
  </script>
</body>
</html>
