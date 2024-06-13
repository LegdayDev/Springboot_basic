## Springboot 2일차
> 1. Oracle Docker 설치
> 2. Database 설정
> 3. SpringBoot + MyBatis 세팅

### 1. Oracle Docker 설치
- `MacOS(M시리즈)`에서는 `Colima` 를 실행하지 않아야 한다.
- `Windows` 에서는 `Oracle` 관련 서비스를 종료해야 한다.
  - `Windows` 에서 `Docker` 설치 시 오류 : `Docker Desktop - WSL Update failed` 오류가 뜰 경우 Windows 업데이트를 실행하여 최신판으로 바꾼 후 [여기](https://github.com/microsoft/WSL/releases)로 들어가서 `wsl.2.x.x.x64.msi` 설치 후 재실행
- Oracle 최신판 설치
  ```shell
  docker --version # Docker 버전
  docker pull container-registry.oracle.com/database/free:latest # Oracle 설치
  docker images # 다운받은 Image 확인
  docker run -d -p 1521:1521 --name oracle container-registry.oracle.com/database/free # Docker oracle 실행 명령어(Windows)
  docker run -d --platform linux/amd64 -p 1521:1521 --name oracle container-registry.oracle.com/database/free # Docker oracle 실행 명령어(MacOS-M시리즈) 
  docker logs oracle 
  docker exec -it oracle bash # Oracle 사용자 설정
  ```
- `Oracle` system 사용자 비밀번호 설정
  ```shell
  ./setPassword.sh oracle # 비밀번호를 `oracle` 로 설정
  ```
---
### 2. Database 설정
- `H2 DB` : Spring Boot에서 손쉽게 사용한 `Inmemory DB`, `Oracle`, `MySql`, `SQLServer`과 쉽게 호환
- `Oracle` : 운영시 사용할 DB
- `MySQL` : Optional 설명할 DB
- `Oracle` : pknusb / pknu_p@ss 로 생성
- `sqlplus` 접속(Docker / 일반 ORACLE) -> `SYSTEM` 계정으로 접속 
  ```shell
  SELECT name FROM v$database; # 서비스명 확인
  ALTER session SET "_oracle_script" = true; # 최신 Oracle 은 C# 언어를 써야하지만 oracle 만 쓴다고 선언
  CREATE user pknusb identified by "pknu_p@ss"; # 계정생성
  grant CONNECT, RESOURCE, CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE VIEW to pknusb; # 권한생성
  ALTER user pknusb default tablespace users; # 사용자 계정 테이블 공간설정
  ALTER user pknusb quota unlimited on users; # 사용자 계정 테이블 공간쿼터
  ```
---
### 3. SpringBoot + MyBatis 
- `application.properties` 세팅
  ```properties
  # Oracle DB 설정
  spring.datasource.username=pknusb
  spring.datasource.password=pknu_p@ss
  spring.datasource.url=jdbc:oracle:thin@localhost:1521:XE
  spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
  
  # MyBatis 설정(mapper 폴더 밑에 여러가지 폴더가 내장, 확장자는 xml이지만 파일명은 뭐든지가능)
  mybatis.mapper-locations=classpath:mapper/**/*.xml
  mybatis.type-aliases-package=com.legday.spring02.domain
  ```
- `Mapper.java` 와 `mapper.xml` 과 매핑 시키기 위해서 `mapper.xml` 에 `namespace` 속성값에 `Mapper.java` 경로를 적어준다.
  ```java
  @Mapper
  public interface TodoMapper {
      List<Todo> selectTodoAll();
  
      Todo selectTodos(int tno);
  }
  ```
  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.legday.spring02.mapper.TodoMapper">
  
      <select id="selectTodos" resultType="com.legday.spring02.domain.Todo">
          SELECT TNO,
                 TITLE,
                 DUEDATE,
                 WRITER,
                 ISDONE
          FROM TODOS;
      </select>
  
      <select id="selectTodoAll" resultType="com.legday.spring02.domain.Todo">
  
  
      </select>
  </mapper>
  ```

