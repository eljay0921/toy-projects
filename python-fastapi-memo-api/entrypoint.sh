#!/usr/bin/env sh
set -e

echo "[entrypoint] migrating..."
echo "[entrypoint] APP_ENV=${APP_ENV:-dev}"
echo "[entrypoint] running alembic migrations..."
alembic upgrade head

echo "[entrypoint] starting..."
if [ "${APP_ENV}" = "local" ] || [ "${APP_ENV}" = "dev" ]; then
  # 개발 편의: 리로드 + 단일 워커
  exec uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
else
  # 운영: gunicorn + uvicorn worker
  exec gunicorn app.main:app \
    -w 2 -k uvicorn.workers.UvicornWorker \
    -b 0.0.0.0:8000 \
    --timeout 60 --graceful-timeout 30 --log-level info
fi