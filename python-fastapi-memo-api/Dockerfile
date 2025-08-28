# ---- build stage ----
FROM python:3.11-slim AS base

ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1

# (선택) tzdata 설치 – TZ 환경변수로 타임존 맞출 때 유용
RUN apt-get update && apt-get install -y --no-install-recommends tzdata && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# 필요한 패키지만 먼저 복사해서 캐시 활용
# requirements.txt가 없으면 아래 RUN pip install ... 라인으로 대체 가능
COPY requirements.txt /app/requirements.txt
RUN pip install --no-cache-dir -r /app/requirements.txt

# 앱 소스 복사
COPY . /app
RUN chmod +x /app/entrypoint.sh

# 기본 실행: uvicorn (compose에서 덮어쓸 수도 있음)
# CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]

# alembic + entrypoint.sh 적용
CMD ["/app/entrypoint.sh"]