# 💁‍♂️SpringBoot-Project(Board)
- SpringBoot와 JPA를 이용한 게시판 프로젝트

<br>

## 📌 프로젝트 개요
- Spring Boot 기반의 게시판 프로젝트로, OAuth2 소셜 로그인, 이메일 인증, 파일 첨부 기능 등을 포함한 사용자 인증 및 게시판 기능을 학습 및 구현하기 위해 개발

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

- OAuth2: Spring Security OAuth2 Client
- 인증 메일: JavaMailSender
- 파일 업로드: Dropzone.js + Spring Multipart
- 테이블 UI: Toast UI Grid

<br>

## 🔧구현 기능
- 회원(Member) : 회원가입, 회원정보 수정, 로그인(OAuth2 포함), 로그아웃 
- 👉 Spring Security를 이용해 인증/인가 처리, 유효성 검증 적용
  
- 게시물(Board) : 게시물 등록(파일 첨부 포함), 게시물 조회, 게시물 수정, 게시물 상세(파일 다운로드 포함), 게시물 삭제
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
|로그인 페이지|Get|/loginPage|
|회원 가입 페이지|Get|/member/joinForm|
|회원 가입|Post|/member/join|
|이메일 인증번호 전송|Post|/member/sendEmail|
|아이디 중복체크|Post|/member/idCheck|
|회원 정보 수정 페이지|Get|/member/updateForm|
|회원 정보 수정|Post|/member/update|
|게시물 목록 페이지|Get|/board/boardList|
|게시물 목록 조회|Get|/board/boardList/data|
|게시물 상세 페이지|Get|/board/boardDetail/{boardId}|
|게시물 수정|Post|/board/updateBoard/{boardId}|
|게시물 삭제|Post|/board/deleteBoard/{boardId}|
|게시물 등록 페이지|Get|/board/writeBoard|
|게시물 등록|Post|/board/saveBoard|
|파일 다운로드|Get|/file/{fileId}/download|
|파일 삭제|Post|/file/deleteFile/{fileId}|
<br>



## 🎓프로젝트 배포 🎓
* 배포 환경 : 리눅스(Ubuntu), Nginx
* 상세 설명 및 과정은 아래 블로그 참고
<br>
https://rhghdrms.tistory.com/34
<br>
https://rhghdrms.tistory.com/35



