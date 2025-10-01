# 작업 일지

## #2025.10.01 (저녁)

1. [설정] 프로젝트 초기 세팅 
   1. starter.spring.io로 초기 구성
   2. 프로젝트 구조 선정 : `DDD` 기반 (light한 설계, 헥사거널은 아님)
   3. 각종 설정 파일 셋업 : build.gradle, application.yml, docker-compose.yml 등 
   4. 빌드 성공 확인 : PostgreSQL, redis 테스트 세팅 포함
2. [설정] `Entity` 정의 (feat. `Claude Code`)
   1. Coupon, CouponIssue
   2. User
3. [설정] `Redis` 설정
   1. RedisConfig, RedisScriptConfig
   2. Lua Script(.lua)
4. [기능 구현] : `쿠폰 발행(issue)`
   1. Controller, DTO, Service, Repository
   2. Service 레벨에서는 Redis만 통신 // DB는 비동기 반영


## 2025.10.02 (새벽)

1. [기능 구현] : `쿠폰 발행 데이터 갱신` (redis -> DB)
   1. `Scheduler`를 이용한 방안 적용 (토이 프로젝트 수준임을 고려)
   2. CouponIssue 엔티티 수정 : 읽기 보다 쓰기 성능을 고려 (결국 하이브리드 전략)
2. [기능 구현] : `쿠폰 정보 CRUD` (feat. `Claude Code`)
   1. 조회, 삭제만 우선 구현
   2. Controller, DTO, Service, Repository
3. [기능 구현] : `쿠폰 기능 단위 테스트 작성` (feat. `Claude Code`)
   1. ~~단위 테스트 작성에 Claude Code 정말 편하...~~
   2. 트러블슈팅(1) : `CouponControllerTest`에서 지속적 실패
      - 원인은 `CouponController`가 `CouponService`뿐 아니라, `CouponIssueService`도 의존하고 있었는데 주입하지 않았기 때문
      - 이때 단위 테스트에서 의존성 주입은 모두 `@MockitoBean` 활용
   3. 트러블슈팅(2) : 부적절한 에러 처리로 일부 단위 테스트 실패 
      - `GlobalExceptionHandler` 생성 및 적용해 해결
   4. 트러블슈팅(3) : `CouponRushRedisApplicationTests`에서 여전히 실패 발생
      - 원인은 `BaseIntegrationTest` 내부에 있었으며, 이때 `Testcontainers로` 동작하는데 내 로컬에 `Docker가` 실행중인 상태가 아니었다. (~~바보 같은...~~)
      - 1차 원인은 `Docker Desktop` 실행 후 해결
      - 이후에도 계속해서 실패했는데, `Hibernate 스미카 검증 오류`가 발생했다.
      - 즉, **마이그레이션**을 위한 조치가 필요했고, 내 프로젝트에는 이를 위해 `Flyway를` 활용할 준비가 되어있다. (다음에 계속)
