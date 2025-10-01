# 작업 일지

## #2025.10.01 (저녁)

1. [설정] 프로젝트 초기 세팅 
   1. starter.spring.io로 초기 구성
   2. 프로젝트 구조 선정 : DDD 기반 (light한 설계, 헥사거널 X)
   3. 각종 설정 파일 셋업 : build.gradle, application.yml, docker-compose.yml 등 
   4. 빌드 성공 확인 : PostgreSQL, redis 테스트 세팅 포함
2. [설정] 엔티티 정의 (feat. Claude Code)
   1. Coupon, CouponIssue
   2. User
3. [설정] Redis 설정
   1. RedisConfig, RedisScriptConfig
   2. Lua Script(.lua)
4. [기능 구현] : 쿠폰 발행(issue)
   1. Controller, DTO, Service, Repository
   2. Service 레벨에서는 Redis만 통신 // DB는 비동기 반영
