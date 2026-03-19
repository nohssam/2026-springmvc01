<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

    /* 댓글 */
    .cmt-wrap { width: 580px; margin: 20px auto; font-size: 14px; }
    .cmt-title { font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 10px; }
    .cmt-item { border: 1px solid black; margin-bottom: 6px; }
    .cmt-head { background: lightsteelblue; padding: 4px 10px; border-bottom: 1px solid black; }
    .cmt-body { padding: 6px 10px; min-height: 40px; }
    .cmt-del-area { background: silver; padding: 4px 10px; border-top: 1px solid black; display: none; }
  </style>

</head>
<body>
<div id="bbs">
  <form method="post" encType="multipart/form-data"  action="${pageContext.request.contextPath}/bbs/writeok">
    <table>
      <caption>게시판 글쓰기</caption>
      <tbody>
      <tr>
        <th class="title" style="width: 20%">제목:</th>
        <td style="text-align: left">${bvo.subject}</td>
      </tr>
      <tr>
        <th class="title">이름:</th>
        <td style="text-align: left">${bvo.writer}</td>
      </tr>
      <tr>
        <th class="title">내용:</th>
        <td style="text-align: left"><textarea name="content" cols="50" rows="8" disabled>${bvo.content}</textarea></td>
      </tr>
      <tr>
        <th class="title">첨부파일:</th>
        <td style="text-align: left">
          <c:choose>
            <c:when test="${not empty bvo.f_name}">
              <a href="${pageContext.request.contextPath}/bbs/download?f_name=${bvo.f_name}">
                <img style="width: 150px" src="${pageContext.request.contextPath}/upload2/${bvo.f_name}" alt="">
              </a>
            </c:when>
            <c:otherwise> 첨부파일 없음</c:otherwise>
          </c:choose>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="hidden" name="b_idx" value="${bvo.b_idx}">
          <input type="hidden" name="nowPage" value="${nowPage}">
          <input type="button" value="수정" onclick="bbs_update(this.form)">
          <input type="button" value="삭제" onclick="bbs_delete(this.form)">
          <input type="button" value="목록" onclick="bbs_list(this.form)">
        </td>
      </tr>
      </tbody>
    </table>
  </form>
</div>
<%-- 댓글 목록 --%>
<div class="cmt-wrap">
<%-- 컨트롤러에서 넘어옴 <div class="cmt-title">댓글: ${c_count}개</div>--%>
  <div class="cmt-title">댓글: ${fn:length(c_list)}개</div>
   <c:choose>
     <c:when test="${empty c_list}">
       <div style="border: 1px solid black; padding: 10px; text-align: center">등록된 댓글이 없습니다.</div>
     </c:when>
     <c:otherwise>
       <c:forEach var="k" items="${c_list}" varStatus="s">
         <div class="cmt-item">
           <div class="cmt-head">
             ${s.count}. <b>${k.writer}</b> &nbsp;&nbsp;${k.write_date}
<%--          나중에는   자기가 쓴사람만 삭제 하게 만들어야 한다.
             <c:if test="${k.writer == '관리자'}">
               <input type="button" value="삭제" onclick="ShowDel('${k.c_idx}')" style="float: right">
             </c:if>
--%>
             <input type="button" value="삭제" onclick="ShowDel('${k.c_idx}')" style="float: right">
           </div>
           <div class="cmt-body"><pre>${k.content}</pre></div>
           <div class="cmt-del-area" id="del-${k.c_idx}">
             <form method="post" action="${pageContext.request.contextPath}/bbs/c_delete">
               <input type="hidden" name="c_idx" value="${k.c_idx}">
               <input type="hidden" name="b_idx" value="${k.b_idx}">
               <input type="hidden" name="nowPage" value="${nowPage}">
               비밀번호 : <input type="password" name="pwd" size="15" required>
               <input type="submit" value="확인">
               <input type="button" value="취소" onclick="hideDel('${k.c_idx}')">
             </form>
           </div>
         </div>
       </c:forEach>
     </c:otherwise>
   </c:choose>
<%-- 댓글 작성 폼  --%>
  <div class="cmt-title" style="margin-top: 20px">댓글 작성</div>
  <form method="post" action="${pageContext.request.contextPath}/bbs/c_insert">
    <input type="hidden" name="b_idx" value="${bvo.b_idx}">
    <input type="hidden" name="nowPage" value="${nowPage}">
    <table>
      <tbody>
      <tr>
        <th class="title" style="width: 20%">작성자</th>
        <td style="text-align: left"><input type="text" name="writer" required></td>
      </tr>
      <tr>
        <th class="title" style="width: 20%">비밀번호</th>
        <td style="text-align: left"><input type="password" name="pwd" required></td>
      </tr>
      <tr>
        <th class="title" style="width: 20%">내용</th>
        <td style="text-align: left">
          <textarea name="content" cols="50" rows="4" required></textarea>
        </td>
      </tr>
      </tbody>
      <tfoot>
        <tr><td colspan="2"> <input type="submit" value="댓글등록"></td></tr>
      </tfoot>
    </table>
  </form>
</div>
<script>
  <c:if test="${not empty pwd_error}">
   window.onload = function (){
     alert("비밀번호 틀림");
     return;
   }
  </c:if>
  function ShowDel(c_idx){
    document.querySelector("#del-"+c_idx).style.display = "block";
    // document.getElementById('del-'+c_idx).style.display="block";
  }
  function hideDel(c_idx){
    document.querySelector("#del-"+c_idx).style.display = "none";
  }
   function bbs_list(f) {
     f.action = "${pageContext.request.contextPath}/bbs/list";
     f.submit();
  }
  function bbs_delete(f) {
    f.action = "${pageContext.request.contextPath}/bbs/deleteForm";
    f.submit();
  }
  function bbs_update(f) {
    location.href = "${pageContext.request.contextPath}/bbs/updateForm?b_idx="+f.b_idx.value+"&nowPage="+f.nowPage.value;
  }
</script>
</body>
</html>

