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
<br>

![login](https://github.com/user-attachments/assets/a367552c-7fce-48ef-bd71-2e2be0eed560)

<br>


## 회원가입 화면

* 회원가입 화면에서 중복 확인과 이메일 인증을 통해, 두 절차가 모두 완료되어야만 가입이 가능하게 구현


<br>


![join](https://github.com/user-attachments/assets/a761087d-6ab9-40a4-9169-ae68b36d5cb8)
![join2](https://github.com/user-attachments/assets/8c56b287-078f-45db-a3ca-1756df49682a)

<br>


## 메인 화면

* 메인 화면에서 게시판 목록 조회, 회원 정보 수정, 로그아웃 기능을 제공
* 소셜 로그인 사용자는 회원 정보 수정 할 수 없게 구현

<br>

![main](https://github.com/user-attachments/assets/a17c044d-14ac-4efd-be23-512b50e7b4af)

<br>



## 상품 담기, 장바구니 내역, 구매 이력 ( 회원용 )

* 상품을 담아 주문을 할때 주문수량이 현재 재고 수보다 클 경우 주문이 되지 않도록 구현하였습니다. &nbsp; exception 패키지를 생성한 후 RuntimeException을 상속받는 OutOfStockException 클래스를 생성하였습니다. &nbsp; 그리고 엔티티 클래스 안에 비즈니스 로직을 메소드로 작성해 코드 재사용과 데이터 변경 포인트를 한군데로 모을수 있는 장점들을 이용했습니다.&nbsp; 주문요청을 처리하기 위해서 상품 주문에서 웹 페이지의 새로 고침 없이 서버에 주문을 요청하기 위해 @RequestBody와 @ResponseBody 어노테이션을 활용해 비동기 방식을 사용했습니다.&nbsp; 상품 상세 페이지에서 구현한 주문 로직을 호출한 코드를 Ajax를 이용하여 주문 로직을 비동기 방식으로 호출했습니다. &nbsp;그리고 주문하기 버튼을 클릭하면 메시지가 뜨며 확인을 누르면 메인 페이지로 이동하게끔 하였습니다. 
* 주문 이력 조회는 OrderRepository @Query 어노테이션을 이용하여 쿼리를 작성하여 구현하였습니다. 
* 주문취소는 주문 수량만큼의 상품의 재고를 증가시키는 메소드와 주문상태를 취소상태로 바꿔주는 메소드를 구현했습니다.&nbsp; OrderController 클래스에 주문번호를 받아서 주문 취소 로직을 호출하는 메소드를 만들어 비동기 요청 방식으로 처리했습니다.
* 상품 상세 페이지에서 장바구니에 담을 상품의 아이디와 수량을 전달 받을 DTO클래스 CartItem을 생성하여 최소 수량은 1개 이상으로 제한하였습니다.&nbsp; 그리고 장바구니에 담을 상품 엔티티를 생성하는 메소드와 장바구니에 담을 수량을 증가시켜주는 메소드를 이 클래스에 추가했습니다. 
* 장바구니 내역 조회 페이지에 전달할 DTO클래스 CartDetailDto를 만들고 장바구니 페이지에 전달할 리스트를 쿼리하나로 조회하는 JPQL문을 CartItemRepository에 작성하였습니다.&nbsp; CartService 클래스에 장바구니 상품의 수량을 업데이트하는 로직을 추가하였습니다.&nbsp; 이때 CartController에서 cartItemId를 파라미터 변수로 받아 @PatchMapping와 @DeleteMapping 어노테이션을 이용하였습니다. &nbsp; 장바구니 상품 삭제는 CartService에 있는 장바구니 상품번호를 파라미터로 받아서 삭제하는 로직을 추가하여 구현하였습니다.



<br>


![회원메인상품선택](https://user-images.githubusercontent.com/106241314/216755879-354d401d-06f8-4611-8a8f-2d24a05bfd73.png)

<br>


![장바구니화면](https://user-images.githubusercontent.com/106241314/216755973-b0b21c3f-cc2e-416b-8d1f-102afd2ea4f3.png)

<br>

![구매이력화면](https://user-images.githubusercontent.com/106241314/216755956-4a73db4a-45b8-48d6-be11-2f52217ffede.png)

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

