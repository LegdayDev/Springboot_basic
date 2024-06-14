## Springboot 2일차
> 1. SpringBoot + MyBatis 세팅 복습
> 2. SpringMVC 구조 개발순서

---
## 1. SpringBoot + MyBatis 세팅 복습
- 의존성에 `MyBatis` 를 추가할 때는 `Springboot 3.3` 아래 버전으로 해야한다!
- 현재 spring02 프로젝트에는 다음과 같은 의존성들이 추가되었다.
  - `Spring Boot DevTools`
  - `Spring Web`
  - `Thymeleaf`
  - `Oracle Driver`
  - `MyBatis Starter`
- 그리고 `application.properties` 파일에서 DB 설정정보들을 적어줘야 한다
---
## 2. SpringMVC 구조 개발순서
1. `Database` 테이블 생성
2. `MyBatis` 설정 -> `/config/MyBatisConfig.java`
3. 테이블과 일치하는 클래스(`domain`, `entity`, `dto`, `vo`, `etc`, ....)
4. DB에 데이터를 주고받을 수 있는 클래긋(`dao`, `mapper`, `repository` ...)
5. 분리했을 경우 `/resources/mapper/~~~.xml` 생성, 쿼리 입력
6. 서비스 인터페이스 `/services/~~~.java`, 서비스 구현 클래스 `/service/~~~Impl.java` 생성하여 작성
7. 사용자 접근하는 컨트롤러 클래스 생성(`@RestController` , `@Controller`)
8. 경우에 따라서 `@SpringBootApplication` 클래스에 `SqlSessionFactory` 빈을 생성
9. `/resources/templates/~~.html` 생성하여 타임리프 작성