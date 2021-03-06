# Proj1
   
## Team
박종회: Contact(Tab1), Crypto-currency Trading Simulation(Tab3), EasterEgg  
송재현: Photos(Tab2)  

## OVERVIEW
2021 몰입캠프의 1주차 과제로 앱을 개발했다. 주어진 과제로는 세개의 탭, 연락처 기능, 갤러리 기능이 있었다. 우리는 위의 내용에 전화, 사진촬영과 더불어 자유 주제로 비트코인 시세를 보며 모의투자를 할 수 있는 기능을 추가해 앱을 더 발전시켜 보았다. 


## ENVIRONMENT
최소로 요구되는 API 버전은 API16(Jelly Bean)이고, 타겟 API는 API30이다. Android Studio emulator로 앱 실행 시, 앱에서 사용되는 두개의 local DB로 인해 성능이 저하될 수 있다.

## 실행 방법
시작 시, 크게 세개의 권한을 받아야 한다. 앱을 처음 실행할 때에 권한을 요청하는데, 이 과정에서 앱이 한번 종료된다. 권한을 다 받고 재실행하면 정상적으로 작동한다.

### CONTACTS)	
1. 사용자 휴대전화에서 연락처를 로드하여 리사이클 뷰로 보여줌.
<img src="https://user-images.githubusercontent.com/82078588/124545891-c5b4fa00-de64-11eb-9e84-c3f901b9be30.jpg" width="200" height="400">
2. 연락처 짧게 클릭하면, 전화번호가 다이얼로 연결됨.
3. 연락처를 길게 클릭하면, 전화번호로 전화가 연결됨.


### PHOTOS)
나만의 갤러리 생성
<br/><img src="https://user-images.githubusercontent.com/82078588/124546038-01e85a80-de65-11eb-8d9a-d85bcdf0b81d.png" width="200" height="400">

1. 사진 선택
<br/>사진 선택 버튼을 누르고 구글 포토 앱으로 넘어가면, 로컬에 있는 갤러리의 사진들에서 원하는 만큼 선택해 올 수 있다. 최대 사진 수가 20개이기 때문에, 총 갯수가 넘어갈 시 불러와지지 않는다. 
2. 카메라를 이용한 사진 촬영
<br/>나만의 갤러리에 담고 싶은 순간이 있다면 카메라를 이용해 사진을 촬영할 수 있다. 사진 촬영 시 화면에 띄워지고, 저장버튼을 통해 휴대전화에 저장된다. 그 다음 메인의  사진 선택을 통해 그 사진을 선택할 수 있다.
<img src="https://user-images.githubusercontent.com/82078588/124546173-34925300-de65-11eb-9e5e-d903e32043f0.png" width="200" height="400">

3. 사진 전체 삭제
<br/>만약 갤러리에 담아둔 사진들을 새로 바꾸고 싶다면 전체 삭제를 클릭하고, 화면을 한번 쓸어내리면 새로고침이 된다.
<img src="https://user-images.githubusercontent.com/82078588/124546228-4aa01380-de65-11eb-8137-9c8ad0178636.png" width="200" height="400">

4. 사진 확대 및 메모
<br/>사진을 클릭하게되면, 사진을 한 화면에 크게 볼 수 있다. 만약 작성하고 싶은 글이 있다면 확대된 사진 아래에 있는 메모란을 활용한다.
<img src ="https://user-images.githubusercontent.com/82078588/124546214-45db5f80-de65-11eb-8655-677c83e5fbbc.png" width="200" height="400">

5. 갤러리에 선택된 사진들은 앱을 재시작하여도 유지된다.


### GAZZZA)
암호화폐 실시간 시세를 이용한 거래 시뮬레이션
1. 스크롤을 위로 올리면(?), 1분 단위로 현재 시세 확인 가능.(무료 API라서 하루에 333번 확인 가능)
<img src="https://user-images.githubusercontent.com/82078588/124546314-65728800-de65-11eb-8498-f20c205b14e3.jpg" width="200" height="400">

2. 상단의 “Search Crypto Currency”에서 원하는 암호화폐 이름 검색
<img src="https://user-images.githubusercontent.com/82078588/124546329-6d322c80-de65-11eb-9a7d-d8901c99d1b2.jpg" width="200" height="400">

3. 암호화폐 아이템을 클릭하면 주문창으로 넘어감. 랜덤하게 조언 Toast 메세지 발생.
<img src="https://user-images.githubusercontent.com/82078588/124546410-8f2baf00-de65-11eb-8e83-d2996cb5f55f.jpg" width="200" height="400">

4. 주문창에서 수량 선택 후, 매수 매도 가능. + 뒤로가기로 원래 창으로 돌아옴.
5. 주문창에서 코인 아이템 클릭 시, 관련 뉴스 웹으로 넘어감.
<img src="https://user-images.githubusercontent.com/82078588/124546391-889d3780-de65-11eb-9210-674809455a35.jpg" width="200" height="400">

6. 우측 하단의 플로팅 버튼 클릭 시, wallet 창으로 넘어감.
<img src="https://user-images.githubusercontent.com/82078588/124546364-7e7b3900-de65-11eb-93f9-675f08f84dd8.jpg" width="200" height="400">

7. 투자 현황과 수익률 계산. (전체 투자 수익률을 수학적으로 정확하지 않을 수 있음.)
8. 휴대전화를 껐다켜도 투자 정보 보존됨.


## 구현 기술과 배운 것들

### CONTACTS)
1. 다른 개발자가 공유한 라이브러리를 통해 연락처를 가져옴. 이 과정에서 자바와 코틀린 코드를 병행하는 법을 배움. 
2. onClick과 onLongClick을 구분하여 구현함. 
3. 특정 상황에서 VideoView와 MediaController를 이용해 전체화면으로 영상을 재생함.


### PHOTOS)
1. Uri를 clipdata로 받아 내부저장소에 비트맵으로 변환 후 저장. recyclerView에 adapt할 uri 추가.
2. ImageAdapter에서 uri를 imageview에 glide를 사용해 표시,
3. SharedPreference를 통해 앱 내부에 사진 갯수 저장.
4. 캐시에서 비트맵을 가져와 다시 uri로 변환해 adapter에 추가. 이 과정에서 로컬에 사진이 재저장되는 문제 발생. 
5. gallery_camera 액티비티 전환으로 카메라에서 찍은 사진을 bitmap으로 받고, 그 후 로컬에 저장.
6. adapter onBindViewHolder에서 itemView에 setOnClickListener을 사용해 사진 uri를 intent로 Image_show 액티비티로 전달해 사진을 확대.
7. Room을 이용한 DB로 각 사진에 대한 메모를 저장.
8. swipe view를 통해 사진 전체 삭제 시 새로고침



### GAZZZA)
1. API를 통해 실시간 시세 데이터를 가져옴.
2. 가져온 JSON Object을 Parse하여 원하는 데이터를 뽑아냄.
3. Adapter의 ArrayList 변수명을 바꾸는 방법으로 검색 기능을 구현함. (filterlist)
4. 해당 fragment와 WalletActivity, OrderActivity 간의 activity 전환을 하며, intent 로 정보를 주고 받음. 
5. NewsWebActivity: 코인의 이름을 통해 구글 검색 URL을 만들어내고, 웹뷰를 통해 뉴스 검색 기능을 추가함.
6. Room을 통해 local DB를 관리함. 이를 통해 코인 구매 내역을 저장하고 수익률을 계산함. 이를 통해 휴대폰을 종료하고 재실행해도 데이터를 보관할 수 있음.
7. swipe view를 통해 새로고침으로 코인 시세를 api에서 리로딩함. 
8. 특정 버튼을 클릭하면, soundPool을 이용하여 음성이 재생되도록 구현함.



## EASTER EGG
1. CONTACTS에서 연락처에 “최준”을 만들고 전화를 걸어본다면?
2. GAZZZA에서 “DOGE”를 사고 팔아 본다면?
