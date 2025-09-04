from datetime import datetime
from typing import List, Optional
from pydantic import BaseModel, Field

class TagCreate(BaseModel):
    name: str = Field(min_length=1, max_length=50)

class TagOut(BaseModel):
    id: int
    name: str
    created_at: datetime

    class Config:
        from_attributes = True

class TagWithArticleCount(BaseModel):
    id: int
    name: str
    article_count: int
    created_at: datetime

    class Config:
        from_attributes = True