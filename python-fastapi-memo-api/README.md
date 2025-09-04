# Memo API (FastAPI)

## TODO

- [x] 프로젝트 초기 세팅
- [x] 모델 정의 : 메모(아티클), 카테고리
- [x] 기능 구현
- [x] 카테고리 모델 수정
- [x] 태그 기능(모델) 추가 완료 🎉
- [ ] ...

## 목적

간단한 메모 기능을 가진 Backend API를 구현했습니다. 본 토이 프로젝트의 목적은 `Python` 및 `FastAPI`를 간단하고 빠르게 학습하기 위한 목적입니다. 본 프로젝트는 `AI`를 활용해 진행했으며, 실무 관점에서는 `Best Practices`가 아닐 수 있습니다. 

> *아래의 내용은 AI를 이용해 자동으로 생성한 내용입니다.

---

### 주요 내용

- FastAPI로 CRUD 라우터 구성
- SQLAlchemy 2.0 스타일 ORM 실습 (관계, 제약, naming convention)
- **Many-to-Many 관계**: Article ↔ Tag (해시태그 기능)
- 환경 분리(.env) + `pydantic-settings`
- Alembic 마이그레이션 및 엔트리포인트 자동 실행
- Docker Compose로 Postgres + API 통합 구동

## Tech Stack

- **FastAPI** (자동 문서: `/docs`, `/redoc`)
- **SQLAlchemy 2.x** (Declarative, Relationship, Naming Convention)
- **PostgreSQL 16**
- **Alembic** (마이그레이션 관리)
- **Pydantic Settings** (환경변수 관리)
- **Uvicorn** (dev) / **Gunicorn(UvicornWorker)** (prod)
- **Docker / docker-compose**

---

## 프로젝트 구조 (요약)

```
python-fastapi-memo-api/
├─ app/
│  ├─ main.py                 # FastAPI 앱/전역 예외 핸들러/라우터 등록
│  ├─ db.py                   # SQLAlchemy 엔진/세션/네이밍 컨벤션
│  ├─ core/
│  │  ├─ settings.py          # pydantic-settings 기반 환경설정 (APP_ENV 매핑)
│  │  └─ errors.py            # AppError/NotFound/Conflict 등 커스텀 오류
│  ├─ models/
│  │  ├─ article.py           # Article 모델 (title/content/category_id/tags...)
│  │  ├─ category.py          # Category 모델 (self-reference, 2단계 제한)
│  │  └─ tag.py               # Tag 모델 (Many-to-Many with Article)
│  ├─ schemas/
│  │  ├─ article.py           # Pydantic in/out 스키마 (태그 지원)
│  │  ├─ category.py
│  │  └─ tag.py               # Tag 관련 스키마
│  └─ routers/
│     ├─ article.py           # /articles CRUD (태그 필터링/관리 포함)
│     ├─ category.py          # /categories CRUD + 트리, 중복 방지, 2단계 제한
│     └─ tag.py               # /tags CRUD + 아티클 연결 관리
├─ alembic/
│  ├─ env.py
│  └─ versions/
│     ├─ 9095defb8518_init_schema.py
│     ├─ 0e6a970a707a_add_description_to_categories.py
│     └─ c2a7f0d053df_add_tag_model_and_many_to_many_.py
├─ requirements.txt
├─ docker-compose.yml
├─ Dockerfile
├─ alembic.ini
└─ entrypoint.sh              # 마이그레이션 → 서버 기동
```

> 참고: `APP_ENV` 값에 따라 `.env` / `.env.local` 등 파일을 읽도록 되어 있습니다. 예: `APP_ENV=local` → `.env.local`

---

## 빠른 시작 (Docker)

### 1) .env 파일 준비

프로젝트 루트에 다음과 같이 준비합니다. (개발/로컬 기준)

```env
# .env.local (또는 .env)
APP_ENV=local
TZ=Asia/Seoul
# docker-compose.yml의 Postgres 컨테이너 기준 연결 문자열
DATABASE_URL=postgresql+psycopg://app:app@db:5432/memo
```

> **주의**: 운영(Postgres 실서버)에서는 `DATABASE_URL`을 적절히 교체하세요.

### 2) 빌드 & 실행

```bash
docker compose up --build
```

- 컨테이너가 기동되면 `entrypoint.sh`에서 **alembic upgrade head**를 실행한 뒤 앱을 시작합니다.
- 개발/로컬 환경(`APP_ENV=local|dev`)에서는 **Uvicorn --reload**로 뜹니다.
- 브라우저에서 **http://localhost:8000/docs** 로 접근해 Swagger UI를 확인합니다.

---

## 직접 실행 (비Docker)

> 로컬 Postgres가 설치되어 있고, 적절한 DB/유저가 준비되어 있다고 가정합니다.

```bash
python -m venv .venv
source .venv/bin/activate   # Windows: .venv\Scripts\activate
pip install -r requirements.txt

# 환경변수
export APP_ENV=local
export DATABASE_URL="postgresql+psycopg://USER:PASSWORD@127.0.0.1:5432/DBNAME"

# 1) Alembic 마이그레이션
alembic upgrade head

# 2) 서버 실행
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

---

## 환경설정

- `APP_ENV`: `local` / `dev` / `prod` 중 하나
  - 매핑: `local → .env.local`, `dev → .env`, `prod → .env`
- `DATABASE_URL`: 예) `postgresql+psycopg://app:app@db:5432/memo`
- `TZ`: 기본값 `Asia/Seoul`

설정 로직은 `app/core/settings.py`의 `pydantic-settings`로 관리됩니다.

---

## 데이터 모델

### Category

- 자기참조 구조 (부모-자식), **최대 2단계** 제한
- 같은 부모(`parent_id`) 아래 **이름 중복 금지** (`UniqueConstraint("parent_id", "name")`)
- 필드: `id`, `name`, `description?`, `parent_id?`, `created_at`

### Tag

- 필드: `id`, `name(unique)`, `created_at`
- 태그명은 **고유**해야 함
- `Tag ↔ Article` Many-to-Many 관계 (`article_tags` 중간 테이블)

### Article

- 필드: `id`, `title(<=200)`, `content(Text)`, `category_id?`, `created_at`, `updated_at`
- `category_id`는 `SET NULL` on delete
- `Article ↔ Category` 양방향 관계
- `Article ↔ Tag` Many-to-Many 관계 (해시태그 기능)

---

## 전역 에러 처리

`app/main.py`와 `app/core/errors.py`에서 아래 패턴으로 에러를 **Problem Details 스타일**로 응답합니다.

```json
{
  "type": "https://errors.memoapi/app-404",
  "title": "Not Found",
  "status": 404,
  "detail": "리소스를 찾을 수 없습니다.",
  "instance": "/articles/999",
  "code": "APP-404",
  "extra": {}
}
```

- `AppError`, `NotFoundError`, `ConflictError` 등 커스텀 예외에 대한 핸들러
- `RequestValidationError`, Starlette `HTTPException`, SQLAlchemy `IntegrityError`, 알 수 없는 예외에 대한 공통 처리

---

## API 개요

> 스키마는 `/docs`에서 확인하세요. 주요 엔드포인트만 요약합니다.

### Health

- `GET /health` → `{ "ok": true }` (간단한 상태 점검)

### Categories

- `POST /categories` → 카테고리 생성
  - Body: `name`, `parent_id?`, `description?`
  - 제약: **최대 2단계**, 부모 없음 or 단일 루트 하위만 허용, **동일 parent 내 중복 이름 금지**
- `GET /categories` → 카테고리 목록
  - 쿼리(예상): `q`(검색), `parent_id` 필터 등
- `GET /categories/tree` → 트리 구조 반환 (루트/자식 포함)
- `GET /categories/{id}` → 단건 조회
- `PATCH /categories/{id}` → 부분 수정
- `DELETE /categories/{id}` → 삭제 (자식/기사와의 관계에 따라 `CASCADE`/`SET NULL` 동작)

### Articles

- `POST /articles` → 기사(메모) 생성
  - Body: `title`, `content`, `category_id?`, `tag_names?` (태그명 배열)
- `GET /articles` → 목록 조회
  - 쿼리: `q`, `category_id`, `tag_id`, 페이지네이션 등
- `GET /articles/{id}` → 단건 조회 (연결된 태그 정보 포함)
- `PATCH /articles/{id}` → 부분 수정 (제공된 필드만 반영, 태그 포함)
- `DELETE /articles/{id}` → 삭제

### Tags

- `POST /tags` → 태그 생성
  - Body: `name` (고유해야 함)
- `GET /tags` → 태그 목록
  - 쿼리: `q`(검색), 페이지네이션, 각 태그별 아티클 개수 포함
- `GET /tags/{id}` → 단건 조회
- `DELETE /tags/{id}` → 삭제 (연결된 아티클과의 관계도 함께 삭제)
- `GET /tags/{id}/articles` → 특정 태그가 달린 아티클 목록

> 실제 지원 쿼리 파라미터/응답 스키마는 코드 기준이며, 문서 UI를 통해 확인하세요.

---

## 예시 요청

```bash
# 1) 카테고리 생성
curl -X POST http://localhost:8000/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "개발",
    "description": "개발 관련 메모 모음"
  }'

# 2) 태그 생성
curl -X POST http://localhost:8000/tags \
  -H "Content-Type: application/json" \
  -d '{"name": "fastapi"}'

# 3) 아티클 생성 (태그와 함께)
curl -X POST http://localhost:8000/articles \
  -H "Content-Type: application/json" \
  -d '{
    "title": "FastAPI 학습 메모",
    "content": "FastAPI와 SQLAlchemy를 사용한 웹 개발",
    "category_id": 1,
    "tag_names": ["fastapi", "python", "web"]
  }'

# 4) 태그별 아티클 필터링
curl "http://localhost:8000/articles?tag_id=1"

# 5) 태그 목록 조회 (아티클 개수 포함)
curl "http://localhost:8000/tags"

# 6) 특정 태그의 아티클들
curl "http://localhost:8000/tags/1/articles"

# 7) 아티클 부분 수정 (태그 수정 포함)
curl -X PATCH http://localhost:8000/articles/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "FastAPI 메모 (수정)",
    "tag_names": ["fastapi", "backend"]
  }'

# 8) 삭제
curl -X DELETE http://localhost:8000/articles/1
```

---

## Alembic

- 마이그레이션은 `entrypoint.sh`에서 **자동 실행**되며, 수동으로는 아래 예시를 따릅니다.

```bash
# 변경사항 반영용 마이그레이션 생성 (autogenerate)
alembic revision --autogenerate -m "update models"
# 적용
alembic upgrade head
# 롤백
alembic downgrade -1
```

> 현재 포함된 리비전 예시  
>
> - `9095defb8518_init_schema.py` (초기)  
> - `0e6a970a707a_add_description_to_categories.py` (카테고리 설명 필드 추가)

---

## 개발 메모

- `SQLAlchemy` naming convention을 지정하여 **제약/인덱스 이름**이 일관되도록 합니다 (`app/db.py`).
- `SessionLocal` + `Depends(get_db)` 패턴으로 세션을 주입합니다.
- 모델 `relationship` 설정으로 **역참조**까지 정의:  
  - `Category.children` (자기참조), `Category.articles`
  - `Article.category`, `Article.tags`
  - `Tag.articles`
- **Many-to-Many 관계**: `article_tags` 중간 테이블로 Article ↔ Tag 연결
- 카테고리 계층은 **2단계 제한** (루트 → 자식까지만)으로 단순화했습니다.
- **태그 자동 생성**: 아티클 생성/수정 시 `tag_names`로 태그 자동 생성 또는 기존 태그 연결
- 전역 에러 응답은 `Problem Details` 스타일로 통일했습니다.

---

## 라이선스

학습용 토이 프로젝트입니다. 필요 시... 필요할 리가 없으니 명시하지 않겠습니다. 😂  