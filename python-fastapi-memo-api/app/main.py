from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
from fastapi.exceptions import RequestValidationError
from starlette.exceptions import HTTPException as StarletteHTTPException
from sqlalchemy.exc import IntegrityError

from app.db import Base, engine
from app.routers import article, category
from app import models
from app.core.errors import AppError

app = FastAPI(title="Memo API v1.0.0", version="1.0.0")
app.include_router(article.router)
app.include_router(category.router)

@app.get("/health")
def health():
    return {"ok" : True}

# --- 공통 응답 유틸 (Problem Details 스타일) ---
def _problem(request: Request, *, status: int, title: str, detail: str, code: str, extra: dict | None = None) -> dict:
    return {
        "type": f"https://errors.memoapi/{code.lower()}",   # example
        "title": title,
        "status": status,
        "detail": detail,
        "instance": str(request.url),
        "code": code,
    } | ({"extra": extra} if extra else {})

# --- 여기서부터 전역 핸들러들 ---

@app.exception_handler(AppError)
async def on_app_error(request: Request, exc: AppError):
    return JSONResponse(
        _problem(request, status=exc.status_code, title=exc.title, detail=exc.detail, code=exc.code, extra=exc.extra),
        status_code=exc.status_code,
    )

@app.exception_handler(StarletteHTTPException)
async def on_http_error(request: Request, exc: StarletteHTTPException):
    # 라우터에서 raise HTTPException(...) 사용 시 통일 포맷으로 반환
    return JSONResponse(
        _problem(request, status=exc.status_code, title="HTTP Error", detail=str(exc.detail), code=f"HTTP-{exc.status_code}"),
        status_code=exc.status_code,
    )

@app.exception_handler(RequestValidationError)
async def on_validation_error(request: Request, exc: RequestValidationError):
    return JSONResponse(
        _problem(request, status=422, title="Validation Error", detail="Request validation failed", code="VAL-422", extra={"errors": exc.errors()}),
        status_code=422,
    )

@app.exception_handler(IntegrityError)
async def on_integrity_error(request: Request, exc: IntegrityError):
    # UNIQUE 위반 → 409 등 상황별로 메시지 가공 가능
    return JSONResponse(
        _problem(request, status=409, title="Conflict", detail="Integrity constraint violated", code="DB-409"),
        status_code=409,
    )

@app.exception_handler(Exception)
async def on_unhandled(request: Request, exc: Exception):
    # 로깅 추가해도 됨 (logging.exception(exc))
    return JSONResponse(
        _problem(request, status=500, title="Internal Server Error", detail="Unexpected server error", code="ERR-500"),
        status_code=500,
    )