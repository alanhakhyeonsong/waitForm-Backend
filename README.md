# waitForm-Backend
INC-CAPSTONE DESIGN 1 - Spring Semester 2022  
산학캡스톤디자인1 - 2022년 봄학기(4학년 1학기), 송학현

Backend Repository 입니다.  
→ [Team Repository 방문하기](https://github.com/ALGO-LEARN/waitForm)

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

4. Service Structure
![](https://velog.velcdn.com/images/songs4805/post/d6e2b836-ff45-4b16-8d68-e4344be1e971/image.png)


# 기술 스택
Backend 프로젝트에서의 기술 스택은 다음과 같습니다.
- Spring Boot
- Spring Data JPA, QueryDSL
- Spring Security, JWT
- Spring WebSocket STOMP
- H2 Database, MySQL
- AWS EC2, AWS RDS
- Docker
- Swagger 3.0

# 프로젝트 구조
Backend 빌드/배포는 다음과 같습니다.

![](https://velog.velcdn.com/images/songs4805/post/7f31dcf4-6cee-45bb-99b7-2d1f2aa849f6/image.png)

## ER Diagram
![](https://velog.velcdn.com/images/songs4805/post/503d4bef-f667-4775-8117-491f1697445f/image.png)


## API Docs
![](https://velog.velcdn.com/images/songs4805/post/c17b0b69-5da6-4b56-972f-28cc10893827/image.png)
![](https://velog.velcdn.com/images/songs4805/post/b35a3456-b5e0-452c-bc3b-6aec05ce3fac/image.png)
![](https://velog.velcdn.com/images/songs4805/post/6a0332ed-97fb-4ceb-9e95-d3da26dfbc61/image.png)

# 작업 내역
[Backend 작업 내역은 다음에서 확인할 수 있습니다.](https://github.com/ALGO-LEARN/waitForm/issues?q=Backend)

// **2022년 6월 18일 이후 commit 내역: Unit Test 추가 및 수정 하였습니다.**

![](https://velog.velcdn.com/images/songs4805/post/2b60d2de-dddf-421d-a8ff-ce2834700739/image.png)

![](https://velog.velcdn.com/images/songs4805/post/57cf0c41-28bf-4b91-9be8-7925eadaa8a0/image.png)

## Convention
### 일관된 API Result 코드
정상 응답의 경우, `ResultCode`및 `ResultResponse`를 미리 정의해두고 `@RestController`에 다음과 같이 통일된 응답 결과를 사용하도록 하였습니다.

```java
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup(@Valid @RequestBody MemberRegisterRequestDto memberRegisterRequestDto) throws Exception {
        MemberResponseDto memberResponseDto = authService.signup(memberRegisterRequestDto);
        ResultResponse result = ResultResponse.of(ResultCode.REGISTER_SUCCESS, memberResponseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    ...
}
```

예외 처리의 경우, `ErrorCode`및 `ErrorResponse`를 미리 정의해두고 각 도메인 별로 발생하는 예외는 `RuntimeException`을 상속받아 만든 `BusinessException`을 상속받아 정의했으며, 이후 `@RestControllerAdvice`을 통해 모든 예외를 핸들링 하는 방식입니다.

- 정상 응답의 경우
```json
{
    "status": 200,
    "code": "M001",
    "message": "회원가입 되었습니다.",
    "data": {
      "email": "test@test.com",
      "nickname": "Ramos"
    }
}
```

- 예외/에러 응답의 경우
```json
{
    "status": 400,
    "code": "C002",
    "message": "invalid input type",
    "errors": [
        {
            "field": "email",
            "value": "test",
            "reason": "이메일의 형식이 맞지 않습니다."
        }
    ]
}
```

## Package Structure
```
└── src
    ├── main
    │   ├── java
    │   │   └── Waitform
    │   │       ├── domain
    │   │       │   ├── member
    │   │       │   │   ├── controller
    │   │       │   │   ├── service
    │   │       │   │   ├── repository
    │   │       │   │   ├── entity
    │   │       │   │   ├── dto
    │   │       │   │   ├── vo
    │   │       │   │   └── exception
    │   │       │   ├── chat
    │   │       │   ├── board
    │   │       │   └── recommend
    │   │       └── global
    │   │           ├── config
    │   │           │   ├── SwaggerConfig.java
    │   │           │   ├── QuerydslConfig.java
    │   │           │   ├── WebSocketConfig.java
    │   │           │   ├── SecurityConfig.java
    │   │           │   ├── firebase
    │   │           │   ├── jwt
    │   │           │   └── util  
    │   │           ├── dto
    │   │           ├── error
    │   │           │   ├── ErrorResponse.java
    │   │           │   ├── GlobalExceptionHandler.java
    │   │           │   ├── ErrorCode.java
    │   │           │   └── exception
    │   │           │       ├── BusinessException.java
    │   │           │       ├── AuthenticationNotFoundException.java
    │   │           │       ├── ...
    │   │           │       └── ...    
    │   │           └── result
    │   │               ├── ResultResponse.java
    │   │               └── ResultCode.java
    │   └── resources
    │       └── application.yml
```
