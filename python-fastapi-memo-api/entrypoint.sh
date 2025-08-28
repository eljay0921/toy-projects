#!/usr/bin/env sh
set -e

echo "[entrypoint] migrating..."
alembic upgrade head

echo "[entrypoint] starting..."
exec gunicorn app.main:app \
  -w 2 -k uvicorn.workers.UvicornWorker \
  -b 0.0.0.0:8000 \
  --timeout 60 --graceful-timeout 30 --log-level info
