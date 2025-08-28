from typing import Optional, List
from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy import or_, select
from sqlalchemy.orm import Session
from app.db import get_db
from app.models import Category, Article
from app.schemas.category import CategoryCreate, CategoryModify, CategoryOut, CategoryTreeOut

router = APIRouter(prefix="/categories", tags=["categories"])

def _ensure_two_levels(db: Session, parent_id: Optional[int]) -> None:
    if parent_id is None:
        return
    
    parent = db.get(Category, parent_id)
    if not parent:
        raise HTTPException(400, "Invalid category parent_id")
    
    if parent.parent_id is not None:
        raise HTTPException(400, "Only two levels are allowd")
    
@router.post("", response_model=CategoryOut, status_code=201)
def create_category(payload: CategoryCreate, db: Session = Depends(get_db)):
    _ensure_two_levels(db, payload.parent_id)

    # 동일 parent 하위에서 중복된 이름의 카테고리가 있는지
    dup = db.query(Category).filter(
        Category.parent_id.is_(payload.parent_id) if payload.parent_id is None else Category.parent_id == payload.parent_id,
        Category.name == payload.name
    ).first()

    if dup:
        raise HTTPException(409, "Category name already exists in this parent.") 
    
    c = Category(name=payload.name, parent_id=payload.parent_id)
    db.add(c)
    db.commit()
    db.refresh(c)

    return c
