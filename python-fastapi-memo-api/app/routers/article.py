from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from typing import Optional
from app.db import get_db
from app.models.article import Article
from app.schemas.article import Create, Modify, Out

router = APIRouter(prefix="/articles", tags=["articles"])

@router.post("", response_model=Out, status_code=201)
def create_article(payload: Create, db: Session = Depends(get_db)):
    e = Article(**payload.model_dump())
    db.add(e)
    db.commit()
    db.refresh(e)
    return e

@router.get("", response_model=list[Out])
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

@router.put("/{article_id}", response_model=Out)
def update_article(article_id: int, payload: Create, db: Session = Depends(get_db)):
    e = db.query(Article).get(article_id)
    if not e:
        raise HTTPException(404, "Article not found")
    
    for k, v in payload.model_dump().items():
        setattr(e, k, v)

    db.commit()
    db.refresh(e)
    return e

@router.patch("/{article_id}", response_model=Out)
def modity_article(article_id: int, payload: Modify, db: Session = Depends(get_db)):
    e = db.query(Article).get(article_id)
    if not e:
        raise HTTPException(404, "Article not found")
    
    update_data = payload.model_dump(exclude_unset=True)
    for k, v in update_data.items():
        setattr(e, k, v)

    db.commit()
    db.refresh(e)
    return e

@router.delete("/{article_id}", status_code=204)
def delete_article(article_id: int, db: Session = Depends(get_db)):
    e = db.query(Article).get(article_id)
    if not e:
        raise HTTPException(404, "Article not found")
    
    db.delete(e)
    db.commit()
    return None
