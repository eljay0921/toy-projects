from datetime import datetime
from typing import Optional
from pydantic import BaseModel, Field

class ArticleCreate(BaseModel):
    title: str = Field(min_langth=1, max_length=200)
    content: str

class ArticleModify(BaseModel):
    title: Optional[str] = Field(default=None, min_length=1, max_length=200)
    content: Optional[str] = None

class ArticleOut(BaseModel):
    id: int
    title: str
    content: str
    created_at: datetime
    updated_at: datetime

    class Config:
        from_attributes = True
