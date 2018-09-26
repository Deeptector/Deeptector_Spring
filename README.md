# Deeptector_Spring
#####  DeepTector는 실시간 행동 분류기 입니다. 이 repository는 Spring Server에 대한 내용입니다.
<br/><br/>
## 구조
- RestFul Api<br/><br/>
  >Web Server와 RestFul기반으로 HTTP 통신을 합니다.
- Socket 통신<br/><br/>
  >DeepLearning Server, Android와 소켓통신을 합니다.
  
 <br/><br/>
 
## 요청
- 동영상 목록 요청<br/><br/>
  >MySQL에 저장되어 있는 동영상의 목록들을 WebServer의 (/getList) 요청을 get방식으로 받아 넘겨준다.
- 감지 알림 요청<br/><br/>
  >DeepLearning Server에서 감지된 알림을 Socket으로 받아 Android에게 Push알림을 보낸다.
- 동영상 Download 요청<br/><br/>
  >Web Server에서 동영상 받기 버튼을 누를 시 요청한 동영상을 Android에게 Socket으로 보낸다.
  
  <br/><br/>
## 프로젝트 관련 링크

- <a href="https://github.com/Deeptector/Deeptector">Deeptector</a>
- <a href="https://github.com/Deeptector/Deeptector_Client_Android">Deeptector_Client_Android</a>
- <a href="https://github.com/Deeptector/Darknet">DarkNet</a>
- <a href="https://github.com/Deeptector/Deeptector_React">Deeptector_React</a>
