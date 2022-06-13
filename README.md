# waitForm-Backend
INC-CAPSTONE DESIGN 1 - Spring Semester 2022  
산학캡스톤디자인1 - 2022년 봄학기(4학년 1학기), 송학현

Backend Repository 입니다.  
// [Team Repository 방문하기](https://github.com/ALGO-LEARN/waitForm)

## Team Members
- Frontend: 김주영
- Backend: 송학현
- ML: 신동원, 유세빈

# 서비스 소개
1. Overview  
Requester는 쉽게 말하면 고용자, Creator는 노동자라고 풀이할 수 있습니다. Requester는 특정 그림체, 화풍, 스킬 보유자 등을 찾으려는 사람입니다. 이 사람들은 원하는 그림이나 글을 올리게 되면 우리의 머신러닝 모델을 통해 원하는 결과를 얻습니다. Creator는 자신의 포트폴리오를 업로드하여 같은 방식으로 모델을 돌려 어느 성향인지 구별해냅니다. 플랫폼의 역할은 그 둘의 사이를 중개, 중재하는 역할입니다.
![](https://velog.velcdn.com/images/songs4805/post/b17f004e-ab7f-487e-9dbc-536bbf4aa4f9/image.png)

2. Service Sequence Diagram  
사용자들은 아래와 같은 프로세스를 거칩니다.
![](https://velog.velcdn.com/images/songs4805/post/f94181a6-e7a2-4f12-8c78-f602a3418032/image.png)

3. Service Details  
플랫폼 내부적으로는 아래와 같이 나눠집니다.
![](https://velog.velcdn.com/images/songs4805/post/683a118b-9144-4b12-b68e-e6efb99f20dc/image.png)


# 기술 스택
Backend 프로젝트에서의 기술 스택은 다음과 같습니다.
- Spring Boot
- Spring Data JPA, QueryDSL
- Spring Security, JWT
- H2 Database, MySQL
- AWS EC2, AWS RDS
- Docker
- Swagger 3.0

# 프로젝트 구조

## ER Diagram
![](https://velog.velcdn.com/images/songs4805/post/f28411ea-27f2-47e0-b0aa-2408a9bab4e9/image.png)


## API Docs
![](https://velog.velcdn.com/images/songs4805/post/d6a34863-d3ea-47e8-9ebd-8b7698654b2b/image.png)
![](https://velog.velcdn.com/images/songs4805/post/013b1a8a-74ac-4635-ae58-117c8b354c60/image.png)
![](https://velog.velcdn.com/images/songs4805/post/89f3b713-73bb-4da1-8fab-51341f0b9af8/image.png)

# 작업 내역
[Backend 작업 내역은 다음에서 확인할 수 있습니다.](https://github.com/ALGO-LEARN/waitForm/issues?q=Backend)

![](https://velog.velcdn.com/images/songs4805/post/2b60d2de-dddf-421d-a8ff-ce2834700739/image.png)

![](https://velog.velcdn.com/images/songs4805/post/57cf0c41-28bf-4b91-9be8-7925eadaa8a0/image.png)
