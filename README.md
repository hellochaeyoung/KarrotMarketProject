# 🥕 초기 당근마켓 백엔드 구축 프로젝트


## 🤲 소개

초기 당근마켓 서버를 구축해보는 프로젝트

넘블(Numble) 챌린지로 시작하고 이후 개인 프로젝트로 진행 중에 있습니다.


## 🗓️ 진행 기간

* 1차 : 2022.01.07 ~ 2022.01.23
* 2차 : 2022.02.01 ~ 현재


## ⚒️ 사용 기술

* **백엔드**
  Spring Boot, Spring MVC, Spring Security, REST API, Spring Data JPA, MySQL

* **프론트엔드**
  Thymeleaf, HTML, Javascript, Ajax
  
* **인프라**
  AWS EC2, AWS RDS, AWS S3
  
## 🐬ERD

![image](https://user-images.githubusercontent.com/55968079/164057395-d9591d14-12dd-485d-826a-32c3359e5c5a.png)


## 🚩기능

**1. 회원 가입 및 로그인**
   
**2. 상품**
   * 지역 및 카테고리로 상품을 조회할 수 있습니다.
   * 상품을 상세 조회할 수 있습니다.
   * 상품 등록자의 타 등록 상품을 미리보기식으로 조회할 수 있습니다.
   * 새 상품을 등록할 수 있습니다.

**3. 댓글**
   * 상품에 댓글을 등록할 수 있습니다.
   * 댓글을 수정, 삭제 할 수 있습니다.

**4. 좋아요**
   * '좋아요' 기능으로 관심 상품에 추가 및 해제할 수 있습니다.

**5. 마이페이지**
   * 프로필을 수정할 수 있습니다.
    - 닉네임
    - 프로필 이미지
   * 등록 상품을 조회, 수정, 삭제할 수 있습니다.
   * 등록 상품의 상품 등록 상태를 변경할 수 있습니다.
   * 관심 목록을 조회할 수 있습니다.
   * 구매 내역을 조회할 수 있습니다.


## 🧐 프로젝트에 대한 기록

* [DTO 적용](https://evening-gooseberry-9aa.notion.site/DTO-Entity-cc78e3ccf90d44f2a6bfe768a7964410)
* [빈과 의존관계 설정](https://evening-gooseberry-9aa.notion.site/b560c90b0ec3451e90359f221adb80c1)
* [로그인 기능 구현 - Session, JWT](https://evening-gooseberry-9aa.notion.site/9521ea6ceb0042a5b2182682ebc448f2)
* [로그인 여부 체크 AOP](https://evening-gooseberry-9aa.notion.site/AOP-41ff25ee6a1e496aa4c928d250d1e3fd)
* [로그인 회원 조회 로직 분리 - ArgumentResolver의 적용](https://evening-gooseberry-9aa.notion.site/ArgumentResolver-224d3d3331d54a3b97ed154b1d6203fc)
     
