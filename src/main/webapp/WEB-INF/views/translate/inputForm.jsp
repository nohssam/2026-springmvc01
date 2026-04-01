<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>번역기 - 영어/베트남어 → 한국어</title>
  <style>
    body {
      font-family: 'Malgun Gothic', sans-serif;
      max-width: 700px;
      margin: 40px auto;
      padding: 0 20px;
      background: #f5f7fa;
    }
    h2 {
      color: #2c3e50;
      border-bottom: 2px solid #27ae60;
      padding-bottom: 8px;
    }
    textarea {
      width: 100%;
      height: 160px;
      padding: 12px;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 14px;
      resize: vertical;
      box-sizing: border-box;
    }
    .lang-select {
      margin: 14px 0;
      font-size: 14px;
    }
    .lang-select label {
      margin-right: 16px;
      cursor: pointer;
    }
    .lang-select span {
      font-weight: bold;
      margin-right: 10px;
    }
    input[type="submit"] {
      padding: 10px 28px;
      background: #27ae60;
      color: #fff;
      border: none;
      border-radius: 6px;
      font-size: 15px;
      cursor: pointer;
    }
    input[type="submit"]:hover {
      background: #1e8449;
    }
    .note {
      margin-top: 16px;
      color: #999;
      font-size: 12px;
    }
  </style>
</head>
<body>

<h2>번역기 (→ 한국어)</h2>

<%-- 번역 입력 폼 (POST /translateOk) --%>
<form action="${pageContext.request.contextPath}/translateOk" method="post">

  <%-- 번역할 원문 입력 --%>
  <p>
        <textarea name="text"
                  placeholder="번역할 문장을 입력하세요 (영어 또는 베트남어)"
                  required></textarea>
  </p>

  <%-- 소스 언어 선택 (라디오 버튼) --%>
  <div class="lang-select">
    <span>소스 언어:</span>
    <%-- 자동 감지: value="" → 서버에서 null/빈값으로 처리 → Google이 언어 감지 --%>
    <label>
      <input type="radio" name="source" value="" checked> 자동 감지
    </label>
    <%-- 영어 고정 --%>
    <label>
      <input type="radio" name="source" value="en"> 영어 (en)
    </label>
    <%-- 베트남어 고정 --%>
    <label>
      <input type="radio" name="source" value="vi"> 베트남어 (vi)
    </label>
  </div>

  <%-- 타깃 언어: 항상 한국어(ko) 고정 → hidden 처리 --%>
  <%-- 서버(TranslateController)에서 target = "ko" 로 고정하므로 폼 전송 불필요 --%>

  <p>
    <input type="submit" value="번역하기">
  </p>
</form>

<p class="note">
  ※ API 키: src/main/resources/google.properties → gcp.translate.api.key<br>
  ※ 타깃 언어는 항상 한국어(ko)로 고정됩니다.
</p>

</body>
</html>
