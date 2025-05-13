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

* 메인화면에는 가구 쇼핑몰의 대표적인 배너와 관리자들이 등록한 상품의 이름, 상세설명, 가격을 보여주었습니다. 
* Thymeleaf Layout Dialect dependency를 추가하여 하나의 layout 페이지를 여러페이지에 적용할 수 있게 하였습니다. &nbsp; 그리고 footer, header를 만들고 layout에서 변경되는 영역엔 fragment="content"로 설정하여 만들페이지를 끼워 넣었습니다.
* Navbar는 부트스트랩을 참고하여 만들었고 header영역에도 추가 하였습니다. &nbsp; 회원가입, 상품등록, 주문 등 이후에 메인페이지로 돌아갈 수 있게 main.html를 만들었습니다.



<br>

![login](https://github.com/user-attachments/assets/a367552c-7fce-48ef-bd71-2e2be0eed560)


<br>


## 로그인, 회원가입

* 회원가입, 로그인은 security dependency를 추가하여 구현하였습니다.&nbsp; 그에 따라 WebSecurityConfigurerAdapter를 상속 받는 클래스에 @EnableWebSecurity 어노테이션을 선언하여 SpringSecurityFilterChain이 자동으로 포함이 되게 하였고 메소드 오버라이딩을 통해 보안 설정을 커스터마이징 하였습니다. 
* SecurityConfig.java를 만들어 보안 설정을 하였습니다.&nbsp; 각각 멤버가 일반 유저인지 관리자인지 구분할 수 있게 Role.java 코드를 작성했습니다.&nbsp; 회원가입에 있어서 멤버가 유저인지 관리자인지 구분할 수 있는 역할이 있어야 했는데 enum 타입의 Role.java 를 만들었습니다.  
*  회원가입 페이지는 부트스트랩을 참고하여 변형하여 사용하였습니다.&nbsp; validation dependency를 추가하여 회원 가입 처리를 할때 유효성 검증을 할 수 있게하여 회원가입이 실패하면 다시 회원 가입 페이지로 이동해 이유를 화면에 명시하였고 회원가입이 성공하면 메인페이지로 리다이렉트 시켜주었습니다.  
*  로그인, 로그아웃은 비밀번호 암호화를 위해 PasswordEncoder를 사용하였고 MemberService가 데이터베이스에서 회원 정보를 가져오는 역할을 하는 UserDetailsService를 구현하게 하여 기능을 만들었습니다.


<br>

![로그인페이지](https://user-images.githubusercontent.com/106241314/216755806-59419d80-ce15-4161-895d-26929597b27f.png)

<br>

![회원가입페이지](https://user-images.githubusercontent.com/106241314/216755814-db85fcee-b4ad-4cec-9801-2059f027bb96.png)

<br>



## 상품등록, 관리 ( 관리자용 )

* 상품등록과 관리는 관리자만 이용할 수 있게 구현하였으며, 관리에서 상품이름에 링크를 걸어 들어가면 수정까지 할 수 있게 하였습니다. 
* 영속성 전이를 위해 연관관계 어노테이션 안에 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하게 해주는 cascade = CascadeType.ALL 옵션을 설정 하였습니다.&nbsp; 그리고 부모 엔티티와 연관관계가 끊어진 자식 엔티티인 고아 객체 제거기능을 위해 위 옵션에 이어 뒤에 orphanRemoval = true 옵션도 추가 설정하였습니다. 
* 서비스를 사용하는데 있어서 오류나 문의사항이 생겼을때 활용이 가능하게 하는 JPA의 Auditing 를 위해 AuditConfig.java를 생성하고 @EnableJpaAuditing 어노테이션을 넣어 기능을 활성화하였습니다.&nbsp; 그리고 등록자와 수정자를 처리해주는 AuditorAwar을 @Bean으로 등록해해 공통으로 엔티티가 저장 또는 수정될 때 자동으로 등록일, 수정일, 등록자, 수정자를 입력해주게 하였습니다.&nbsp; 보통 테이블에는 위의 4가지를 모두 넣어주지만 어떤 테이블은 등록자, 수정자를 넣지않아 BaseTimeEntity만 상속받을 수 있도록 BaseTimeEntity를 생성하였습니다.&nbsp; 그리고 상속받을 수 있게 BaseEntity도 생성했습니다. 
* 상품등록과 관리의 수정에 사용할 데이터 전달을 위해 이를 도와주는 modelmapper 라이브러리를 추가하여 원활하게 해주었습니다.&nbsp; 상품의 이미지파일을 등록할때 경로 지정을 위해 application.properties에 itemLmgLocation을 추가하여 파일을 읽어올 경로를 설정 하였고 WebMvcConfigurer 인터페이스를 구현하는 WebMvcConfig.java 파일을 작성하였습니다.&nbsp; 그다음 addResourceHandlers 메소드를 통해 자신의 컴퓨터에 업로드한 파일을 찾을 위치를 설정하였습니다.&nbsp; 이 파일을 처리할 FileService.java를 만들어 UUID로 받은 값과 원래의 파일 이름 확장자를 조합해 저장될 파일이름을 만들고, FileOutputStream 클래스를 이용하여 파일이 저장될 위치와 파일에 쓸 파일 출력 스트림을 만들었습니다. 
* 상품 관리를 위해 조회를 할 때 @Transactional(readOnly = true)를 설정하여 성능을 향상 시켰습니다.&nbsp; 그리고 수정기능을 추가 하였습니다. 이미지 수정은 상품 등록과 같이 itemImgRepository.save() 로직을 호출하지 않고 영속상태의 데이터를 변경하는 것만으로 변경 감지 기능이 동작하게 하여 트랜잭션이 끝날 때 update 쿼리가 실행되도록 하였습니다.


<br>

![상품등록화면](https://user-images.githubusercontent.com/106241314/216755823-4ad60866-5cb8-4caa-89a4-9ca371bf9b20.png)

<br>

![상품관리화면](https://user-images.githubusercontent.com/106241314/216755826-1aaadfbb-4f45-451f-86a5-ca4462f9ec13.png)

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

