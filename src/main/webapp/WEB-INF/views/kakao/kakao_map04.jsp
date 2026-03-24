<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
   <h2>카카오 맵 샘플 04 - 키워드로 장소 검색</h2>
   <input type="text" name="keyword" id="keyword">
   <button type="button" onclick="search_go()">검색</button>

   <!-- 지도를 표시할 div 입니다 -->
   <div id="map" style="width:100%;height:350px;"></div>

   <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=af4f6dd21202814ba878a2f2c1b257cc&libraries=services"></script>
   <script>
      // 자바스크립트에서 내 위치(위도와 경도)
      // navigator.geolocation.getCurrentPosition(position => {
      //    const lat = position.coords.latitude;
      //    const lng = position.coords.longitude;
      //    geo_map(lat, lng);
      // });
      function search_go() {
         const keyword = document.querySelector('#keyword').value
         console.log(keyword);
         geo_map(keyword)
      }

      function geo_map(keyword) {
         // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
         var infowindow = new kakao.maps.InfoWindow({zIndex:1});
         // 지도 자동 생성
         var mapContainer = document.getElementById('map'), // 지도를 표시할 div
                 mapOption = {
                    center: new kakao.maps.LatLng(0, 0), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                 };

         var map = new kakao.maps.Map(mapContainer, mapOption);

         // 장소 검색 객체를 생성
         var ps = new kakao.maps.services.Places();

         // 키워드로 장소를 검색합니다
         ps.keywordSearch(keyword, placesSearchCB);

         function placesSearchCB(data, status, pagination) {
            if(status === kakao.maps.services.Status.OK) {
               // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
               // LatLngBounds 객체에 좌표를 추가합니다
               var bounds = new kakao.maps.LatLngBounds();
               for (var i = 0; i < data.length; i++) {
                  displayMarker(data[i]);
                  bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
               }
               // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
               map.setBounds(bounds);

            }
         }
         // 지동에 마커를 표시하는 함수
         function displayMarker(place) {
            var marker = new kakao.maps.Marker({
               map: map,
               position: new kakao.maps.LatLng(place.y, place.x),
            });
            // 마커에 클릭이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'click', function() {
               // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
               infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
               infowindow.open(map, marker);
            });
         }
      }

   </script>
</body>
</html>
