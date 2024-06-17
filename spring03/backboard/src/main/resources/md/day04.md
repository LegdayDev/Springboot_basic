## Springboot 4일차
> 1. SpringBoot JPA + Oracle + Thymeleaf + React
> 2. Spring Boot JPA 프로젝트 생성
> 3. Spring Boot JPA 프로젝트 개발시작 전 설정
> 4. Spring Boot JPA 프로젝트 개발 시작
---
### 1. SpringBoot JPA + Oracle + Thymeleaf
- JPA
  - JPA 는 DB설계를 하지 않고 엔티티 클래스를 DB로 자동생성 해주는 기술
  - Query 로 만들 필요가 없다.
- Thymeleaf
  - JSP의 단점인 복잡한 템플릿 형태와 스파게티코드를 해소해주는 템플릿
- Bootstrap
  - 웹 디자인 및 CSS의 혁신 !
- 소셜로그인 - `Oauth2 Client`
  - Google , Kakao , Naver 등으로 로그인 할 수 있는 기능 구현
- React
  - 프론트엔드를 분리 !
  - 프론트엔드 서버와 백엔드 서버를 따로 관리
---
### 2. Spring Boot JPA 프로젝트 생성
1. **명령 팔레트 시작** : `cmd + shift + p` (VsCode 기준) 
2. **Spring Initialzr**: Create a Gradle Project
3. **Spring Boot version** : 3.2.6
4. **Language** : Java
5. **Group ID** : com.legday
6. **Artifact ID** : backboard
7. **Packaging Type** : Jar
8. **Java Version** : 17
9. **Dependencies**
    - `Spring Boot DevTools`
    - `Lombok`
    - `Spring Web`
    - `Thymeleaf`
    - `H2 Database`(later)
    - `Oracle Driver`(later)
    - `Spring Data JPA`(later)
---
### 3. Spring Boot JPA 프로젝트 개발시작 전 설정
- `build.gradle` 확인
- `application.properties` 기본설정 입력(포트번호, 자동빌드, 로그레벨)
- `application.properties` 에서 H2 DB 설정, JPA 설정
- 웹 서버 실행 `http://localhost:8080/h2-console` 입력하여 DB 연결확인
---
### 4. Spring Boot JPA 프로젝트 개발 시작
- 각 기능별로 폴더를 생성(controller , service, entity ...) -> MVC 모델 
- `/controller/MainController.java` 생성
- `/entity/Board.java` 생성
  - `GenerationType` 타입
    - `AUTO` : `SpringBoot` 에서 자동으로 선택(비추천)
    - `IDENTITY` : `MySQL` , `SQLServer`
    - `SEQUENCE` : `Oracle`
  - column 이름을 카멜케이스(creatDate)로 만들면 DB에는 스네이크케이스(CREATE_DATE)로 자동변환된다.
- `/entity/Reply.java` 생성
- `Board` 엔티티와 `Reply` 엔티이간 연관관계(`@OneToMany` , `@ManyToOne`)를 설정
- 웹 서버 재시작 후 콘솔에 SQL 과 `h2-console` 확인하여 테이블 생성확인 
-  `/repository/BoardRepository.java` , `/repository/ReplyRepository.java` 인터페이스 생성
  - `JpaRepository<엔티티타입, 식별자타입>` 을 상속받는다.
    - `JpaRepository` 를 상속받으면 `@Repository` 를 적지 않아도 `Component Scan` 이 되어 Bean 등록이 된다.
- Junit5 테스트
  - `@SpringBootTest` 어노테이션을 적어서 테스트 시작시 스프링 컨테이너를 띄운다.
  - `@Autowired` 로 `BoardRepository` 를 주입받는다.
  - 테스트 메서드 위에 Junit5 테스트는 기본적으로 **테스트 종료 시 rollback** 을 한다.(rollback 을 막기 위해 `@Rollback(value=false)` 를 선언해주면 insert 쿼리와 DB에 값을 확인할 수 있다.)
> H2 는 내장 H2가 아니라 별도의 파일을 다운받아 따로 실행(2.2.224)버전 실행