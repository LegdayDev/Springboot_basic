## Springboot 1일차
> 1. Spring Boot 개요 
> 2. Spring Boot 개발환경 설정
> 3. Spring Boot 프로젝트 생성
---
### 1. Spring Boot 개요
- 개발환경, 개발 난이도를 낮추는 작업
- `Servlet` > `EJB` > `JSP` > `Spring` > `Spring Boot`
- 장점
    - `Spring` 의 기술을 그대로 사용가능(마이그레이션 간단)
    - `JPA(Java Persistence API)`를 사용하면 `ERD`나 `DB`설계를 하지 않고도 손쉽게 DB 생성
    - `Tomcat Webserver`가 내장(따로 설치필요x)
    - 서포트 기능 다수 존재(개발을 쉽게 도와줌)
    - JUnit 테스트, Log4J2 로그도 모두 포함
    - JSP, **Thymeleaf**, Mistache 등... 편하게 사용가능
    - DB 연동이 무지 쉽다
---
### 2. Spring Boot 개발환경 설정
- Java JDK 확인 > 17버전 이상
    - [OpenJDK 다운로드](http://jdk.java.net/archive/)
    - 시스템 속성 > 고급 > 환경변수 중 `JAVA_HOME` 설정

- Visual Studio Code
    - VSCodeUserSetup-x64-1.90.0.exe 아님 설치하지 말것
    - VSCodeSetup-x64-1.90.0.exe로 설치할 것
    - Extensions > Korean 검색, 설치
    - Extensions > Java 검색, Extension Pack for Java 설치
        - Debugger for Java 등 6개 확장팩이 같이 설치
    - Extensions > Spring 검색, Spring Extension Pack 설치
        - Spring Initializr Java Support 등 3개의 확장팩 같이 설치
    - Extensions > Gradle for Java 검색, 설치
- Gradle build tool 설치 고려
    - [Gradle 다운로드](https://gradle.org/releases/)
- Oracle latest version Docker - 보류

### 3. Spring Boot 프로젝트 생성
- 메뉴 보기 > 명령 팔레트(Cmd + Shift + P)
  - `Spring Initializr`: Create a Gradle Project...
  - `Specify Spring Boot version` : 3.2.6
  - `Specify project language` : Java
  - `Input Group Id` : (개인적 변경할 것)
  - `Input Artifact Id` : spring01 (대문자 불가!)
  - `Specify packaing type` : Jar
  - `Specify Java version` : 17
  - `Choose dependencies` : Selected 0 dependencies
- 폴더 선택 Diaglog 팝업 : 원하는 폴더 선택 Generate ... 버튼 클릭
- 오른쪽하단 팝업에서 Open 버튼 클릭
- Git 설정 옵션, Language Support for Java(TM) by Red Hat 설정 항상버튼 클릭

- `TroubleShooting`
  1. 프로젝트 생성이 진행되다 Gradle Connection 에러가 뜨면,
      - Extensions > Gradle for Java를 제거
      - VS Code 재시작한뒤 프로젝트 재성성
  2. Gradle 빌드시 버전 에러로 빌드가 실패하면
      - Gradle build tool 사이트에서 에러에 표시된 버전의 Gradle bt 다운로드
      - 개발 컴퓨터에 설치
  3. ':compileJava' execution failed...
      - JDK 17 ..... error 메시지
      - Java JDK 잘못된 설치 x86(32bit) x64비트 혼용 설치
      - eclipse adoptium jdk 17 새로 설치, 시스템 환경설정
      - build.gradle SpringBoot Framework 버전을 다운 3.3.0 -> 3.2.6

- 프로젝트 생성 후
  - `/build.gradle` 확인
  - `src/main/resources/application.properties` (또는, .yml)확인
  - `src/java/groupid/arifactid/` Java 소스파일 위치, 작업
  - `src/main/resources/` 프로젝트설정 파일, 웹 리소스 파일(css, js, html, jsp, ...)
  - `Spring01Application.java` Run | Debug 메뉴
  - `Gradle` 빌드
      - 터미널에서 `.\gradlew.bat` 실행
      - Gradle for java(코끼리 아이콘) > Tasks > Build > Build play icon(Run task) 실행
  - `Spring Boot Dashboard`
      - `Apps > Spring01` Run | Debug 중에서 하나 아이콘 클릭 서버 실행
      - 디버그로 실행해야 Hot code replace 가 동작!!!

- 브라우저 변경설정
    - `설정(Ctrl + ,)` > `browser` > `Spring` > `Dashboard Open With 'Internal'` -> `external`로 변경
    - Chrome 을 기본브라우저 사용 추천
---