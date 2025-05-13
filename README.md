# 💁‍♂️SpringBoot-Project(Board)
- SpringBoot와 JPA를 이용한 게시판 프로젝트

<br>



## ⚙프로젝트 개발 환경
- 운영체제 : Microsoft Windows 11
- 통합개발환경(IDE) : IntelliJ
- JDK Version : JDK 17
- 스프링부트 Version : 3.4.3
- 데이터베이스 : MySQL
- 빌드툴 : Gradle
- 관리툴 : Git, GitHub

<br>

## 📗프로젝트 기술 스택📗
- <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" /> <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white" /> <img src="https://img.shields.io/badge/JS-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white" /> <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=Bootstrap&logoColor=white" /> <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white" />
- <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" /> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white" />
- <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white" /> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white" />

<br>

## 🔧구현 기능
- 회원(Member) : 회원가입, 회원정보 수정, 로그인(OAuth2 포함), 로그아웃 
<br>     < Spring Security를 이용하여 구현, 유효성 검증 사용 >
- 게시물(Board) : 게시물 등록, 게시물 조회, 게시물 수정, 게시물 상세, 게시물 삭제
<br>    

## 로그인 화면

* 로그인 화면에서 로그인, 소셜로그인, 회원가입 기능을 제공
* Spring Security 활용하여 구현
* 소셜 로그인 시 사용자가 별도의 회원가입 절차 없이 바로 가입되는 방식으로 구현

![login](https://github.com/user-attachments/assets/a367552c-7fce-48ef-bd71-2e2be0eed560)

<br>


## 회원가입 화면

* 회원가입 화면에서 중복 확인과 이메일 인증을 통해, 두 절차가 모두 완료되어야만 가입이 가능하게 구현

![join](https://github.com/user-attachments/assets/a761087d-6ab9-40a4-9169-ae68b36d5cb8)
![join2](https://github.com/user-attachments/assets/8c56b287-078f-45db-a3ca-1756df49682a)

<br>


## 메인 화면

* 메인 화면에서 게시판 목록 조회, 회원 정보 수정, 로그아웃 기능을 제공
* 소셜 로그인 사용자는 회원 정보 수정 할 수 없게 구현

![main](https://github.com/user-attachments/assets/a17c044d-14ac-4efd-be23-512b50e7b4af)

<br>



## 회원정보 수정 화면

* 소셜 로그인 사용자가 아닌 회원가입을 통하여 로그인한 사용자가 정보를 수정할 수 있게 구현

![updateMember](https://github.com/user-attachments/assets/adb4000f-0312-4808-bd12-0ec5553430ed)

<br>


## 게시판 목록 화면

* 검색 기능 구현
* Toast UI Grid 활용하여 테이블 구현
* Toast UI Grid API 활용하여 필터링과 페이징 처리
* 게시물 등록 기능 제공

![boardList](https://github.com/user-attachments/assets/07f8877f-1fa6-4cc9-a8ed-4832c1d67594)

<br>

## 게시판 등록 화면

* 작성자는 로그인한 사용자의 아이디로 고정
* 제목과 내용은 필수값
* Dropzone 라이브러리의 API를 활용하여 파일 업로드 구현 (drag & drop 제거)
  
![registerBoard](https://github.com/user-attachments/assets/91490f48-b1a2-4791-a8d7-cfcb6e6e6765)

<br>

## 게시판 상세 화면 ( 작성자 / 일반 사용자 )

* 한 페이지 내에서 작성자 여부에 따라 화면 및 기능을 구분하여 구현
* 작성자 : 파일첨부, 파일 다운로드(서버에 파일이 있을 경우), 삭제, 수정 기능
* 일반 사용자 : 읽기 전용, 파일 다운로드 기능

![boardDetail(writer)](https://github.com/user-attachments/assets/1b02b6c7-d661-40c7-a87a-1aef2abf1d33)
![boardDetail(reader)](https://github.com/user-attachments/assets/5e3488b7-bdd8-43a0-a7af-01880160edd8)

<br>



## 💻API 명세서
|View|Method|End Point|
|:----:|:----:|:----:|
|메인 페이지|Get|/|
|회원 가입 페이지|Get|/members/new|
|회원 가입|Post|/members/new|
|로그인 에러 페이지|Get|/members/login/error|
|로그인|Get|/members/login|
|상품 등록|Post|/admin/item/new|
|상품 등록 페이지|Get|/admin/item/new|
|상품 조회|Get|/admin/item/{itemId}|
|상품 수정|Post|/admin/item/{itemId}|
|상품 관리|Get|/admin/items, /admin/items/{page}|
|상품 상세 페이지|Get|/item/{itemId}|
|장바구니 담기|Post|/cart|
|장바구니 페이지|Get|/cart|
|장바구니 상품 수정|Patch|/cartItem/{cartItemId}|
|장바구니 상품 제거|Delete|/cartItem/{cartItemId}|
|장바구니 상품 주문|Post|/cart/orders|
|주문하기|Post|/order|
|주문 내역 페이지|Get|/orders, /orders/{page}|
|주문 취소|Post|/order/{orderId}/cancel|
<br>



## 🎓프로젝트 개발 동기와 하면서 느낀점🎓

이 프로젝트를 개발한 동기는 자바, 스프링부트, 스프링 시큐리티, JPA 등의 기술을 이론적으로 숙지한 뒤 이런것들을 어떻게 활용하고 


이를 통해 서버가 작동하는 원리가 궁금하여 학원에서 배운 기초지식과 이것들을 확장해줄 강의를 듣고 구체적인 서적을 참고하여 간단한 쇼핑몰을 만들어보게 되었다.


완벽하지 않지만 나만의 프로젝트를 개발하는 것이 백엔드 개발의 전체적인 흐름을 잡을 수 있고 


개발하다 오류가 생겼을때나 궁금한 점들을 찾아가며 해결하는 과정을 통해 개발자로 가는 첫걸음 뗀다고 생각하여 이번 프로젝트에서 구글링을 통해 검색해가며 최선을 다했다. 


이 과정에서 뿐만아니라 프로젝트 개발을 마무리하고 나서도 이와 관련된 개념 또한 잘 숙지하며 공부를 하고 있다.

