# Coupon Rush Redis

선착순 쿠폰 발급 시스템 (Redis + PostgreSQL)

## 프로젝트 개요

~~그냥 토이 프로젝트입니다.~~  
대용량 트래픽 환경에서 선착순 쿠폰 발급을 안전하게 처리하기 위한 시스템입니다.
Redis를 활용하여 race condition을 방지하고, PostgreSQL에 주기적으로 동기화합니다.

## 기술 스택

- **Java 17**
- **Spring Boot 3.5.6**
- **Redis** - 쿠폰 발급 처리 (원자성 보장)
- **PostgreSQL** - 데이터 영구 저장
- **Flyway** - 데이터베이스 마이그레이션
- **Testcontainers** - 통합 테스트
- **Lombok** - 보일러플레이트 코드 제거

## 주요 설계

### Race Condition 해결
Redis의 원자적 연산(INCR, SETNX 등)을 활용하여 동시성 제어

### 성능 최적화
- CouponIssue 엔티티에 user_id, coupon_id 컬럼 직접 포함 (쓰기 성능 우선)
- 조회용 연관관계는 선택적으로 사용

### 데이터 일관성
Redis 발급 내역을 주기적으로 PostgreSQL에 동기화

## TODO
- [ ] Flyway 마이그레이션 스크립트 작성
- [x] Redis 기반 쿠폰 발급 로직 구현
- [x] Redis → DB 동기화 스케줄러 구현
- [ ] 단위 & 통합 테스트 작성
- [ ] API 문서화 (Swagger/OpenAPI)
