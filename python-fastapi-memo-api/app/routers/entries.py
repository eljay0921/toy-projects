from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from typing import Optional
from app.db import get_db
from app import models, schemas

router = APIRouter(prefix="/entries", tags=["entries"])

@router.post("", response_model=schemas.EntryOut, status_code=201)
def create_entry(payload: schemas.EntryCreate, db: Session = Depends(get_db)):
    e = models.Entry(**payload.model_dump())
    db.add(e)
    db.commit()
    db.refresh(e)
    return e

@router.get("", response_model=list[schemas.EntryOut])
def list_entries(
    q: Optional[str] = None,
    page: int = Query(1, ge=1),
    size: int = Query(20, qe=1, le=100),
    db: Session = Depends(get_db),
):
    query = db.query(models.Entry)
    if q:
        query = query.filter(models.Entry.title.ilike(f"%{q}%")) | (models.Entry.content.ilike(f"%{q}%"))
    
    rows = query.order_by(models.Entry.created_at.desc()).offset((page - 1) * size).limit(size).all()
    return rows

@router.put("/{entry_id}", response_model=schemas.EntryOut)
def update_entry(entry_id: int, payload: schemas.EntryCreate, db: Session = Depends(get_db)):
    e = db.query(models.Entry).get(entry_id)
    if not e:
        raise HTTPException(404, "Entry not found")
    
    for k, v in payload.model_dump().items():
        setattr(e, k, v)

    db.commit()
    db.refresh(e)
    return e

@router.patch("/{entry_id}", response_model=schemas.EntryOut)
def modity_entry(entry_id: int, payload: schemas.EntryModify, db: Session = Depends(get_db)):
    e = db.query(models.Entry).get(entry_id)
    if not e:
        raise HTTPException(404, "Entry not found")
    
    update_data = payload.model_dump(exclude_unset=True)
    for k, v in update_data.items():
        setattr(e, k, v)

    db.commit()
    db.refresh(e)
    return e

@router.delete("/{entry_id}", status_code=204)
def delete_entry(entry_id: int, db: Session = Depends(get_db)):
    e = db.query(models.Entry).get(entry_id)
    if not e:
        raise HTTPException(404, "Entry not found")
    db.delete(e)
    db.commit()
    return None
