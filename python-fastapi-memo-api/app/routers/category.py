from typing import Optional, List
from fastapi import APIRouter, Depends, Query
from sqlalchemy import or_, select
from sqlalchemy.orm import Session
from app.db import get_db
from app.models import Category, Article
from app.schemas.category import CategoryCreate, CategoryModify, CategoryOut, CategoryTreeOut
from app.schemas.article import ArticleOut
from app.core.errors import AppError, ConflictError, NotFoundError

router = APIRouter(prefix="/categories", tags=["categories"])

def _ensure_two_levels(db: Session, parent_id: Optional[int]) -> None:
    if parent_id is None:
        return
    
    parent = db.get(Category, parent_id)
    if not parent:
        raise AppError("부모 카테고리 ID가 잘못되었습니다.")
    
    if parent.parent_id is not None:
        raise AppError("카테고리는 최대 2단계까지 허용됩니다.")
    
@router.post("", response_model=CategoryOut, status_code=201)
def create_category(payload: CategoryCreate, db: Session = Depends(get_db)):
    _ensure_two_levels(db, payload.parent_id)

    # 동일 parent 하위에서 중복된 이름의 카테고리가 있는지
    dup = db.query(Category).filter(
        Category.parent_id.is_(payload.parent_id) if payload.parent_id is None else Category.parent_id == payload.parent_id,
        Category.name == payload.name
    ).first()

    if dup:
        raise ConflictError("카테고리 이름이 중복됩니다.") 
    
    c = Category(
        name=payload.name, 
        parent_id=payload.parent_id, 
        description=payload.description,
    )
    db.add(c)
    db.commit()
    db.refresh(c)

    return c

@router.get("", response_model=List[CategoryOut])
def list_categories(
    parent_id: Optional[int] = Query(None, description="부모 ID 지정 시, 해당 부모의 하위만 조회"),
    db: Session = Depends(get_db)
):
    q = db.query(Category).filter(
        Category.parent_id.is_(parent_id) if parent_id is None else Category.parent_id == parent_id
    ).all()

    return q

@router.get("/tree", response_model=List[CategoryTreeOut])
def list_categories_tree(db: Session = Depends(get_db)):
    parents = db.query(Category).filter(Category.parent_id.is_(None)).order_by(Category.name.asc()).all()
    
    def to_node(ct: Category) -> CategoryTreeOut:
        return CategoryTreeOut(
            id=ct.id, name=ct.name, description=ct.description, parent_id=ct.parent_id, created_at=ct.created_at, 
            children=[CategoryTreeOut(id=ch.id, name=ch.name, description=ch.description, parent_id=ch.parent_id, created_at=ch.created_at, children=[]) for ch in ct.children]
        )

    return [to_node(c) for c in parents]

@router.patch("/{category_id}", response_model=CategoryOut)
def modify_category(category_id: int, payload: CategoryModify, db: Session = Depends(get_db)):
    c = db.get(Category, category_id)
    if not c:
        raise NotFoundError("카테고리를 찾을 수 없습니다.")
    data = payload.model_dump(exclude_unset=True)

    # parent_id 변경 검증
    if "parent_id" in data:
        new_parent_id = data["parent_id"]
        if new_parent_id == category_id:
            raise AppError("자기 자신을 부모 카테고리로 설정할 수 없습니다.")
        
        _ensure_two_levels(db, new_parent_id)
        if new_parent_id is not None and len(c.children) > 0:
            # 부모(자식을 가진) 카테고리를 다른 카테고리의 자식으로 내리면 안 됨 (최대 2단계)
            raise AppError("이 카테고리는 자식 카테고리를 보유하고 있습니다. (최대 2단계)")

    # 이름 중복 체크
    if "name" in data and data["name"] is not None:
        dup = db.query(Category).filter(
            Category.id != category_id,
            (Category.parent_id.is_(data.get("parent_id")) if data.get("parent_id") is None else Category.parent_id == data.get("parent_id")),
            Category.name == data["name"]
        ).first()

        if dup:
            raise ConflictError("카테고리 이름이 중복됩니다.") 

    for k, v in data.items():
        setattr(c, k, v)
    
    db.commit()
    db.refresh(c)

    return c

@router.delete("/{category_id}", status_code=204)
def delete_category(category_id: int, force: bool = False, db: Session = Depends(get_db)):
    category = db.get(Category, category_id)
    if not category:
        raise NotFoundError("카테고리를 찾을 수 없습니다.")

    children_cnt = db.query(Category).filter(Category.parent_id == category_id).count()
    articles_cnt = db.query(Article).filter(Article.category_id == category_id).count()

    if not force and (children_cnt > 0 or articles_cnt > 0):
        raise ConflictError(f"In use (children={children_cnt}, articles={articles_cnt}). Use ?force=true to delete.")

    db.delete(category)  # 부모 삭제 시 자식은 CASCADE, 기사 category_id는 NULL(SET NULL)
    db.commit()
    return None

@router.get("/{category_id}/articles", response_model=List[ArticleOut])
def list_articles_by_category(
    category_id: int,
    include_children: bool = Query(False, description="자식 카테고리의 글을 포함할지 여부"),
    page: int = Query(1, ge=1),
    size: int = Query(20, ge=1, le=100),
    db: Session = Depends(get_db)
):
    # 카테고리 확인
    category = db.get(Category, category_id)
    if not category:
        raise NotFoundError("카테고리를 찾을 수 없습니다.")
    
    # 쿼리
    query = db.query(Article)
    
    if include_children:
        children_ids = select(Category.id).where(Category.parent_id == category_id)
        query = query.filter(
            or_ (
                Article.category_id == category_id,
                Article.category_id.in_(children_ids),
            )
        )
    else:    
        query = query.filter(Article.category_id == category_id)

    rows = (
        query.order_by(Article.created_at.desc())
                .offset((page - 1) * size)
                .limit(size)
                .all()
    )

    return rows
