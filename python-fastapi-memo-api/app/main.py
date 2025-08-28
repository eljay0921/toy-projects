from fastapi import FastAPI
from app.db import Base, engine
from app.routers import article

Base.metadata.create_all(bind=engine)

app = FastAPI(title="Memo API v1.0.0", version="1.0.0")
app.include_router(article.router)

@app.get("/health")
def health():
    return {"ok" : True}
