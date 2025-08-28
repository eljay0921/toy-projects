from typing import Optional, List
from datetime import datetime
from pydantic import BaseModel, Field

class CategoryCreate(BaseModel):
    name: str = Field(min_length=2, max_length=50)
    parent_id: Optional[int] = None

class CategoryModify(BaseModel):
    name: Optional[str] = Field(default=None, min_length=2, max_length=50)
    parent_id: Optional[int] = None

class CategoryOut(BaseModel):
    id: int
    name: str
    parent_id: Optional[int] = None
    created_at: datetime

    class Config:
        from_attributes = True
    
class CategoryTreeOut(BaseModel):
    id: int
    name: str
    parent_id: Optional[int] = None
    created_at: datetime
    children: List["CategoryTreeOut"] = []

    class Config:
        from_attributes = True

CategoryTreeOut.model_rebuild()
