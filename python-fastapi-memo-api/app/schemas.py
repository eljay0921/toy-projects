from datetime import datetime
from pydantic import BaseModel, Field

class EntryCreate(BaseModel):
    title: str = Field(min_langth=1, max_length=200)
    content: str
    entry_date: datetime

class EntryOut(BaseModel):
    id: int
    title: str
    content: str
    entry_date: datetime
    created_at: datetime
    updated_at: datetime

    class Config:
        from_attributes = True
