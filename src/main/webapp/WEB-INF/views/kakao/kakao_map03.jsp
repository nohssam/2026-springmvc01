<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
   <h2>카카오 맵 샘플 03 - 내 위치 마커,인포 표시</h2>
   <!-- 지도를 표시할 div 입니다 -->
   <div id="map" style="width:100%;height:350px;"></div>

   <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=af4f6dd21202814ba878a2f2c1b257cc"></script>
   <script>
      // 자바스크립트에서 내 위치(위도와 경도)
      navigator.geolocation.getCurrentPosition(position => {
         const lat = position.coords.latitude;
         const lng = position.coords.longitude;
         geo_map(lat, lng);
      });
      function geo_map(lat, lng) {
         var mapContainer = document.getElementById('map'), // 지도를 표시할 div
                 mapOption = {
                    center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                 };

         var map = new kakao.maps.Map(mapContainer, mapOption);

         // 마커가 표시될 위치입니다
         var markerPosition  = new kakao.maps.LatLng(lat, lng);

          // 마커를 생성합니다
         var marker = new kakao.maps.Marker({
            position: markerPosition
         });

         // 마커가 지도 위에 표시되도록 설정합니다
         marker.setMap(map);
         // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
         var iwContent = '<div style="padding:5px;"><a href="https://ictedu.co.kr" target="_blank"><small>한국ICT인재개발원</small></a></div>',
                 iwPosition = new kakao.maps.LatLng(lat, lng); //인포윈도우 표시 위치입니다

         // 인포윈도우를 생성합니다
         var infowindow = new kakao.maps.InfoWindow({
            position : iwPosition,
            content : iwContent
         });

      // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
         infowindow.open(map, marker);
      }

   </script>
</body>
</html>
