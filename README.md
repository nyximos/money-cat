# money-cat
<img src="https://github.com/user-attachments/assets/36358e6d-7799-422f-8e43-420715d57d59" alt="moneycat" width="500"/>
<br>
<br>

Money-cat은 사용자들의 예산 관리와 지출 추적을 전문으로 하는 플랫폼으로,  
사용자가 개인 재정을 효율적으로 관리할 수 있도록 다양한 기능을 제공합니다.  

자신의 예산을 설정하고 카테고리별 예산을 추천받아 쉽게 예산을 설정할 수 있습니다.  
사용자는 지출 내역을 관리할 수 있으며, 월별 예산을 기준으로 오늘 소비 가능한 금액을 카테고리별로 추천받을 수 있습니다.  

지난달/지난 요일/다른 사용자와의 지출 비교 통계를 통해 자신의 소비 패턴을 분석하여  
보다 건강한 소비 습관을 형성하고 재정 목표를 달성할 수 있도록 돕습니다.
<br>
<br>

### 목차
[1. 개발 환경](#1-개발-환경)

[2. 기능](#2-기능)

[3. 브랜치 전략](#3-브랜치-전략)

[4. Getting Started](#4-getting-started)

[5. 데이터베이스](#5-데이터베이스)

[6. 프로젝트 구조](#6-프로젝트-구조)  
<br>
# 1. 개발 환경

- Java 21
- Spring Boot 3.3.3
- Gradle
- MySQL
- Redis
- JPA
- QueryDSL
- JWT
<br>

# 2. 기능

| 번호 | Method | URL | **Authorization** | Description |
| --- | --- | --- | --- | --- |
| 1 | POST | localhost:8086/apis/users |  | 사용자 회원가입 |
| 2 | POST | localhost:8086/apis/users/login |  | 사용자 로그인 및 토큰 발급 |
| 3 | GET | localhost:8086/apis/categories | ✔️ | 모든 카테고리 조회 |
| 4 | POST | localhost:8086/apis/budget | ✔️ | 예산 설정 |
| 5 | POST | localhost:8086/apis/budget/recommend |  | 예산 추천 |
| 6 | POST | localhost:8086/apis/spending | ✔️ | 지출 생성 |
| 7 | PUT | localhost:8086/apis/spending/{id} | ✔️ | 지출 수정 |
| 8 | DELETE | localhost:8086/apis/spending/{id} | ✔️ | 지출 삭제 |
| 9 | GET | localhost:8086/apis/spending/{id} | ✔️ | 지출 상세 조회 |
| 10 | GET | localhost:8086/apis/spending | ✔️ | 지출 목록 조회 |
| 11 | GET | localhost:8086/apis/spending/summary/today | ✔️ | 오늘 지출 요약 조회 |
| 12 | GET | localhost:8086/apis/spending/summary/today | ✔️ | 오늘 지출 추천 조회 |
| 13 | GET | localhost:8086/apis/spending/statistics | ✔️ | 지출 통계 조회 |
<br>

# 3. 브랜치 전략

**Branch - Git Flow**

- main : 배포 단계
- develop : 개발 단계
- feat : 기능 단위
<br>

**Commit**

| Commit Type | Description |
| --- | --- |
| chore | 빌드 수정, Production Code 변경 없음 |
| feat | 새로운 기능 |
| refactor | 기능, 코드 개선 |
| docs | 문서 수정 |
| remove | 파일을 삭제 |
| comment | 주석 추가 및 변경 |
<br>

# 4. Getting Started

**create .env file**

```
# 서버 관련 설정
SERVER_PORT=

# 데이터베이스 연결 정보
DB_URL=
DB_USERNAME=
DB_PASSWORD=

# JWT 정보
JWT_KEY=

# REDIS
REDIS_HOST=
REDIS_PORT=
```
<br>

**Clone the Repository**

```
git clone https://github.com/nyximos/money-cat
cd money-cat
```
<br>

**Build the Project**

```
./gradlew build
```
<br>

**Run the Application**

`./gradlew bootRun`
<br>
<br>

# 5. 데이터베이스

[DB Diagram](https://www.erdcloud.com/d/hfokAG4Leofkj9vfs)
<br>

<img width="1030" alt="image" src="https://github.com/user-attachments/assets/8b06715e-1606-4b8f-8e48-5e94cee63a14">
<br>
<br>

# 6. 프로젝트 구조

```
├── HELP.md
├── README.md
├── build
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── moneycat
    │   │           ├── MoneyCatApplication.java
    │   │           ├── budget
    │   │           │   ├── controller
    │   │           │   │   ├── api
    │   │           │   │   │   ├── BudgetController.java
    │   │           │   │   │   ├── CategoryController.java
    │   │           │   │   │   ├── SpendingController.java
    │   │           │   │   │   ├── TokenController.java
    │   │           │   │   │   └── UserController.java
    │   │           │   │   └── model
    │   │           │   │       ├── request
    │   │           │   │       │   ├── BudgetRecommendationRequest.java
    │   │           │   │       │   ├── BudgetRequest.java
    │   │           │   │       │   ├── LoginRequest.java
    │   │           │   │       │   ├── SignUpRequest.java
    │   │           │   │       │   ├── SpendingRequest.java
    │   │           │   │       │   └── SpendingSearchRequest.java
    │   │           │   │       └── response
    │   │           │   │           ├── BudgetRecommendationResponse.java
    │   │           │   │           ├── CategoryRecommendationResponse.java
    │   │           │   │           ├── CategoryResponse.java
    │   │           │   │           ├── CategorySpending.java
    │   │           │   │           ├── CategorySpendingRateResponse.java
    │   │           │   │           ├── RecommendationResponse.java
    │   │           │   │           ├── SpendingDetailResponse.java
    │   │           │   │           ├── SpendingResponse.java
    │   │           │   │           ├── StatisticsResponse.java
    │   │           │   │           ├── SummaryResponse.java
    │   │           │   │           └── TokenResponse.java
    │   │           │   ├── converter
    │   │           │   │   ├── BudgetConverter.java
    │   │           │   │   ├── RefreshTokenConverter.java
    │   │           │   │   ├── SpendingConverter.java
    │   │           │   │   └── UserConverter.java
    │   │           │   ├── persistence
    │   │           │   │   └── repository
    │   │           │   │       ├── BudgetRepository.java
    │   │           │   │       ├── CategoryRepository.java
    │   │           │   │       ├── RefreshTokenRepository.java
    │   │           │   │       ├── SpendingRepository.java
    │   │           │   │       ├── UserRepository.java
    │   │           │   │       ├── custom
    │   │           │   │       │   ├── BudgetRepositoryCustom.java
    │   │           │   │       │   └── SpendingRepositoryCustom.java
    │   │           │   │       ├── entity
    │   │           │   │       │   ├── BaseEntity.java
    │   │           │   │       │   ├── BudgetEntity.java
    │   │           │   │       │   ├── CategoryEntity.java
    │   │           │   │       │   ├── RefreshTokenEntity.java
    │   │           │   │       │   ├── SpendingEntity.java
    │   │           │   │       │   └── UserEntity.java
    │   │           │   │       └── impl
    │   │           │   │           ├── BudgetRepositoryImpl.java
    │   │           │   │           └── SpendingRepositoryImpl.java
    │   │           │   └── service
    │   │           │       ├── BudgetCategoryPercentageDto.java
    │   │           │       ├── BudgetService.java
    │   │           │       ├── CategoryService.java
    │   │           │       ├── SpendingService.java
    │   │           │       ├── TokenProvider.java
    │   │           │       ├── TokenService.java
    │   │           │       ├── UserService.java
    │   │           │       ├── delegator
    │   │           │       │   ├── LoginValidationDelegator.java
    │   │           │       │   └── validator
    │   │           │       │       ├── AccessPermissionValidator.java
    │   │           │       │       ├── DuplicateEmailValidator.java
    │   │           │       │       ├── LoginValidator.java
    │   │           │       │       ├── PasswordValidator.java
    │   │           │       │       ├── RefreshTokenValidator.java
    │   │           │       │       └── TokenValidator.java
    │   │           │       └── dto
    │   │           │           ├── BudgetSpendingDto.java
    │   │           │           └── MonthlyBudgetDto.java
    │   │           └── core
    │   │               ├── config
    │   │               │   ├── DefaultJpaRepository.java
    │   │               │   ├── DefaultRedisRepository.java
    │   │               │   ├── JpaConfig.java
    │   │               │   ├── PasswordEncoderConfig.java
    │   │               │   ├── QueryDslConfig.java
    │   │               │   └── SwaggerConfig.java
    │   │               ├── constant
    │   │               │   └── MoneyCatConstants.java
    │   │               ├── exception
    │   │               │   ├── CategoryNotFoundException.java
    │   │               │   ├── EmailAlreadyInUseException.java
    │   │               │   ├── ErrorCode.java
    │   │               │   ├── InvalidPasswordException.java
    │   │               │   ├── InvalidRefreshTokenException.java
    │   │               │   ├── InvalidTokenException.java
    │   │               │   ├── MoneyCatException.java
    │   │               │   ├── SpendingNotFoundException.java
    │   │               │   ├── UnauthorizedAccessException.java
    │   │               │   └── UserNotFoundException.java
    │   │               ├── filter
    │   │               │   └── TokenValidationFilter.java
    │   │               ├── handler
    │   │               │   ├── MoneyCatExceptionHandler.java
    │   │               │   └── RequestHandler.java
    │   │               ├── util
    │   │               │   ├── SpendingUtils.java
    │   │               │   └── TokenUtils.java
    │   │               └── wrapper
    │   │                   ├── ResultResponse.java
    │   │                   └── TokenUser.java
    │   └── resources
    │       └── application.yml
    └── test
        └── java
            └── com
                └── moneycat
                    ├── MoneyCatApplicationTests.java
                    ├── budget
                    │   └── service
                    │       ├── BudgetServiceTest.java
                    │       └── SpendingServiceTest.java
                    └── core
                        └── util
                            └── SpendingUtilsTest.java
```


