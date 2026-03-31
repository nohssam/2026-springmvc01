
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #container{width:100%; margin: 0 auto; text-align: center;}
        #city-buttons {margin: 15px 0;}
        table { margin: 0 auto; border-collapse: collapse; }
        th, td { border: 1px solid #999; padding: 6px 12px; text-align: center;}
        thead tr { background-color: #f0f0f0; }
    </style>
</head>
<body>

<div id="container">
    <h2>기상청 단기 예보 - xml</h2>
    <div id="city-buttons">
        <button class="city-btn active" data-nx="60" data-ny="127">서울</button>
        <button class="city-btn" data-nx="98" data-ny="76">부산</button>
    </div>
    <hr>
    <div id="result"></div>
</div>

<script src="https://code.jquery.com/jquery-4.0.0.js"></script>
<script>
    // 하늘상태    맑음(1), 구름많음(3), 흐림(4)
    function getSky(code){
        return {'1' : '맑음','3' : '구름많음','4' : '흐림' }[code] || '-'
    }
    // pty;       // 강수 형태   없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
    function getPty(code) {
        return {'0' : '-', '1' : '비', '2' : '비/눈', '3' : '눈', '4' : '소나기'}[code] || '-'
    }
    // 날짜 수정 ("20260329")
    function formatDate(d){
        return d.substring(0,4)+"." +d.substring(4,6)+"." + d.substring(6,8);
    }
    // 시간 수정 ("06:00")
    function formatTime(t){
        return t.substring(0,2) + ":" + t.substring(2,4) ;
    }
    function loadWeather(nx, ny){
        $("#result").html("<p>불러오는 중 ...</p>");
        $.ajax({
            url : "${pageContext.request.contextPath}/weatherXml", // 서버에 갈 주소 ( Rest 엔드 포인트)
            method: "GET",               // 방식
            data : {nx:nx, ny:ny},       // 서버에 갈때 가져갈 데이터
            dataType : "json",            // 서버에서 가져온 데이터 타입
            success : function(data){
                if(! data || data.length == 0){
                    $("#result").html("<p style='color:red;'>데이터 없음</p>");
                }

                let table = "<table>";
                table += "<thead><tr>";
                table +=  "<th>날짜</th><th>시간</th><th>기온</th><th>하늘상태</th>";
                table +=  "<th>강수형태</th><th>강수확률(%)</th><th>강수량</th><th>습도</th><th>풍속</th>";
                table += "</tr></thead>";
                table += "<tbody>";

                let preData = '';
                $.each(data, function(i, vo){
                    // 날짜가 이전 행과 다를 때만 날짜 출력
                    const isNewDate = vo.fcstDate !== preData ;
                    preData = vo.fcstDate;

                    table += "<tr>";
                    table +=  "<td>"+(isNewDate ? formatDate(vo.fcstDate) : '') +"</td>";
                    table +=  "<td>"+formatTime(vo.fcstTime)+"</td>";
                    // vo.tmp 값이 있으며 그 값 사용 없으면 - 사용
                    table +=  "<td>"+(vo.tmp || '-')+"</td>";
                    table +=  "<td>"+getSky(vo.sky)+"</td>";
                    table +=  "<td>"+getPty(vo.pty)+"</td>";
                    table +=  "<td>"+(vo.pop || '-')+"</td>";
                    table +=  "<td>"+(vo.pcp && vo.pcp !== '강수없음' ? vo.pcp : '-') + "</td>";
                    table +=  "<td>"+(vo.reh || '-')+"</td>";
                    table +=  "<td>"+(vo.wsd || '-')+"</td>";
                    table += "</tr>";
                });

                table += "</tbody></table>";
                $("#result").html(table);
            },
            error : function(){
                $("#result").html("<p style='color:red;'>데이터 로드 실패 </p>");
            }
        });
    }

    $(document).ready(function(){
        // 서울, 부산 버튼을 클릭했을 때
        $('button[data-nx]').on('click', function(){
            loadWeather($(this).data('nx'), $(this).data('ny'));
        });
        // 페이지 시작하자 마자 실행하자
        loadWeather(60,127);

    });
</script>
</body>
</html>
