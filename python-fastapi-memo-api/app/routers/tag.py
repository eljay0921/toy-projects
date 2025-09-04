from typing import List, Optional
from fastapi import APIRouter, Depends, Query
from sqlalchemy import func, select
from sqlalchemy.orm import Session
from app.db import get_db
from app.models import Tag, Article
from app.models.tag import article_tag_association
from app.schemas.tag import TagCreate, TagOut, TagWithArticleCount
from app.core.errors import ConflictError, NotFoundError

router = APIRouter(prefix="/tags", tags=["tags"])

@router.post("", response_model=TagOut, status_code=201)
def create_tag(payload: TagCreate, db: Session = Depends(get_db)):
    # 중복 태그명 확인
    existing_tag = db.query(Tag).filter(Tag.name == payload.name).first()
    if existing_tag:
        raise ConflictError("이미 존재하는 태그명입니다.")
    
    tag = Tag(name=payload.name)
    db.add(tag)
    db.commit()
    db.refresh(tag)
    
    return tag

@router.get("", response_model=List[TagWithArticleCount])
def list_tags(
    q: Optional[str] = Query(None, description="태그명 검색"),
    page: int = Query(1, ge=1),
    size: int = Query(20, ge=1, le=100),
    db: Session = Depends(get_db)
):
    query = (
        db.query(
            Tag,
            func.count(article_tag_association.c.article_id).label("article_count")
        )
        .outerjoin(article_tag_association)
        .group_by(Tag.id)
    )
    
    if q:
        query = query.filter(Tag.name.ilike(f"%{q}%"))
    
    tags = (
        query.order_by(Tag.name.asc())
        .offset((page - 1) * size)
        .limit(size)
        .all()
    )
    
    return [
        TagWithArticleCount(
            id=tag.id,
            name=tag.name,
            article_count=count,
            created_at=tag.created_at
        )
        for tag, count in tags
    ]

@router.get("/{tag_id}", response_model=TagOut)
def get_tag(tag_id: int, db: Session = Depends(get_db)):
    tag = db.get(Tag, tag_id)
    if not tag:
        raise NotFoundError("태그를 찾을 수 없습니다.")
    
    return tag

@router.delete("/{tag_id}", status_code=204)
def delete_tag(tag_id: int, db: Session = Depends(get_db)):
    tag = db.get(Tag, tag_id)
    if not tag:
        raise NotFoundError("태그를 찾을 수 없습니다.")
    
    db.delete(tag)
    db.commit()
    
    return None

@router.get("/{tag_id}/articles")
def list_articles_by_tag(
    tag_id: int,
    page: int = Query(1, ge=1),
    size: int = Query(20, ge=1, le=100),
    db: Session = Depends(get_db)
):
    # 태그 확인
    tag = db.get(Tag, tag_id)
    if not tag:
        raise NotFoundError("태그를 찾을 수 없습니다.")
    
    # 해당 태그가 달린 아티클들 조회
    articles = (
        db.query(Article)
        .join(article_tag_association)
        .filter(article_tag_association.c.tag_id == tag_id)
        .order_by(Article.created_at.desc())
        .offset((page - 1) * size)
        .limit(size)
        .all()
    )
    
    return articles