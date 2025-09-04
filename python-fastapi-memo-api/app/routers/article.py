from fastapi import APIRouter, Depends, Query
from sqlalchemy.orm import Session, joinedload
from sqlalchemy import or_
from typing import Optional, List
from app.db import get_db
from app.models.article import Article
from app.models.tag import Tag
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
    # tag_names를 제외한 나머지 필드로 Article 생성
    article_data = payload.model_dump(exclude={"tag_names"})
    article = Article(**article_data)
    
    # 태그 처리
    if payload.tag_names:
        article.tags = _get_or_create_tags(db, payload.tag_names)
    
    db.add(article)
    db.commit()
    db.refresh(article)
    return article

@router.get("", response_model=list[ArticleOut])
def list_articles(
    q: Optional[str] = None,
    tag_id: Optional[int] = Query(None, description="태그 ID로 필터링"),
    category_id: Optional[int] = Query(None, description="카테고리 ID로 필터링"),
    page: int = Query(1, ge=1),
    size: int = Query(20, ge=1, le=100),
    db: Session = Depends(get_db),
):
    query = db.query(Article).options(joinedload(Article.tags))
    
    if q:
        query = query.filter(or_(
            Article.title.ilike(f"%{q}%"),
            Article.content.ilike(f"%{q}%")
        ))
    
    if category_id:
        query = query.filter(Article.category_id == category_id)
    
    if tag_id:
        query = query.join(Article.tags).filter(Tag.id == tag_id)
    
    rows = query.order_by(Article.created_at.desc()).offset((page - 1) * size).limit(size).all()
    return rows

@router.put("/{article_id}", response_model=ArticleOut)
def update_article(article_id: int, payload: ArticleCreate, db: Session = Depends(get_db)):
    article = _ensure_exist_article(db, article_id)
    
    # tag_names를 제외한 나머지 필드 업데이트
    article_data = payload.model_dump(exclude={"tag_names"})
    for k, v in article_data.items():
        setattr(article, k, v)
    
    # 태그 완전 교체
    if payload.tag_names is not None:
        article.tags = _get_or_create_tags(db, payload.tag_names)

    db.commit()
    db.refresh(article)
    return article

@router.patch("/{article_id}", response_model=ArticleOut)
def modify_article(article_id: int, payload: ArticleModify, db: Session = Depends(get_db)):
    article = _ensure_exist_article(db, article_id)

    update_data = payload.model_dump(exclude_unset=True, exclude={"tag_names"})
    for k, v in update_data.items():
        setattr(article, k, v)
    
    # 태그 업데이트 (제공된 경우에만)
    if "tag_names" in payload.model_dump(exclude_unset=True):
        article.tags = _get_or_create_tags(db, payload.tag_names or [])

    db.commit()
    db.refresh(article)
    return article

def _get_or_create_tags(db: Session, tag_names: List[str]) -> List[Tag]:
    """태그명 목록으로부터 Tag 객체들을 가져오거나 생성"""
    tags = []
    for name in tag_names:
        name = name.strip()
        if not name:
            continue
            
        tag = db.query(Tag).filter(Tag.name == name).first()
        if not tag:
            tag = Tag(name=name)
            db.add(tag)
            db.flush()  # ID를 얻기 위해 flush
        tags.append(tag)
    return tags

@router.get("/{article_id}", response_model=ArticleOut)
def get_article(article_id: int, db: Session = Depends(get_db)):
    article = db.query(Article).options(joinedload(Article.tags)).filter(Article.id == article_id).first()
    if not article:
        raise NotFoundError("아티클을 찾을 수 없습니다.")
    return article

@router.delete("/{article_id}", status_code=204)
def delete_article(article_id: int, db: Session = Depends(get_db)):
    article = _ensure_exist_article(db, article_id)
    
    db.delete(article)
    db.commit()
    return None
