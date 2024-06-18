## Springboot 5일차
> 1. Spring Boot JPA 프로젝트 개발 

---
### 1. Spring Boot JPA 프로젝트 개발
- `Junit5` 테스트코드 `Update` , `Select` , `Delete` 구현
- `/service/BoardService.java` 생성후 `boardList()` 메서드 작성
- `/controller/BoardController.java` 생성 후 `@GetMapping` 으로 URL 설정
- `/templates/board/list.html` 생성 후 타임리프 세팅
  - `<html>` 태그 안에 `xmlns:th="http://www.thymeleaf.org"` 를 넣어줘야 한다.
  - 타임리프 문법을 통해 모든 데이터 테이블에 추가
  - 각 데이터의 타이틀을 누르면 상세페이지로 이동
- `/templates/board/detail.html` 생성 후 타임리프 세팅
  - 위와 동일하게 데이터 갖고와서 뿌려주기