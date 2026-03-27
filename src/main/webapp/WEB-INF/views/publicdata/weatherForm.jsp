<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #container{width:100%; margin: 0 auto; text-align: center;}
        #city-buttons {margin: 15px 0;}
     </style>
</head>
<body>

  <div id="container">
      <h2>기상청 단기 예보</h2>
      <div id="city-buttons">
        <button class="city-btn active" data-nx="60" data-ny="127">서울</button>
        <button class="city-btn" data-nx="98" data-ny="76">부산</button>
      </div>
      <hr>
      <div id="loading">&#8987; 데이터를 불러오는 중 ...</div>
      <div id="result"></div>
  </div>


</body>
</html>
