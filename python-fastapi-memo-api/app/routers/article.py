from fastapi import APIRouter, Depends, Query
from sqlalchemy.orm import Session
from typing import Optional
from app.db import get_db
from app.models.article import Article
from app.schemas.article import ArticleCreate, ArticleModify, ArticleOut
from app.core.errors import NotFoundError

router = APIRouter(prefix="/articles", tags=["articles"])

def _ensure_exist_article(db: Session, article_id: Optional[int]):
    if not article_id :
        return None
    
    article = db.get(Article, article_id)
    if not article:
        raise NotFoundError("아티클을 찾을 수 없습니다.")
    
    return article


@router.post("", response_model=ArticleOut, status_code=201)
def create_article(payload: ArticleCreate, db: Session = Depends(get_db)):
    e = Article(**payload.model_dump())
    db.add(e)
    db.commit()
    db.refresh(e)
    return e

@router.get("", response_model=list[ArticleOut])
def list_articles(
    q: Optional[str] = None,
    page: int = Query(1, ge=1),
    size: int = Query(20, qe=1, le=100),
    db: Session = Depends(get_db),
):
    query = db.query(Article)
    if q:
        query = query.filter(Article.title.ilike(f"%{q}%")) | (Article.content.ilike(f"%{q}%"))
    
    rows = query.order_by(Article.created_at.desc()).offset((page - 1) * size).limit(size).all()
    return rows

@router.put("/{article_id}", response_model=ArticleOut)
def update_article(article_id: int, payload: ArticleCreate, db: Session = Depends(get_db)):
    article = _ensure_exist_article(db, article_id)
    
    for k, v in payload.model_dump().items():
        setattr(article, k, v)

    db.commit()
    db.refresh(article)
    return article

@router.patch("/{article_id}", response_model=ArticleOut)
def modity_article(article_id: int, payload: ArticleModify, db: Session = Depends(get_db)):
    article = _ensure_exist_article(db, article_id)

    update_data = payload.model_dump(exclude_unset=True)
    for k, v in update_data.items():
        setattr(article, k, v)

    db.commit()
    db.refresh(article)
    return article

@router.delete("/{article_id}", status_code=204)
def delete_article(article_id: int, db: Session = Depends(get_db)):
    article = _ensure_exist_article(db, article_id)
    
    db.delete(article)
    db.commit()
    return None
