from datetime import datetime, timezone
from typing import TYPE_CHECKING, List, Optional
from app.db import Base
from sqlalchemy import Integer, DateTime, String, ForeignKey, UniqueConstraint
from sqlalchemy.orm import Mapped, mapped_column, relationship

if TYPE_CHECKING:
    from .article import Article

class Category(Base):
    __tablename__ = "categories"
    __table_args__ = (
        UniqueConstraint("parent_id", "name", name="uq_categories_parent_name"),
    )

    id: Mapped[int] = mapped_column(Integer, primary_key=True)
    name: Mapped[str] = mapped_column(String(50), index=True)
    parent_id: Mapped[Optional[int]] = mapped_column(ForeignKey("categories.id", ondelete="CASCADE"), nullable=True, index=True)

    # 자기참조
    parent: Mapped[Optional["Category"]] = relationship(back_populates="children", remote_side="Category.id")
    children: Mapped[List["Category"]] = relationship(
        back_populates="parent", cascade="all, delete-orphan", passive_deletes=True, single_parent=True
    )
    # 역참조
    articles: Mapped[List["Article"]] = relationship(
        back_populates="category", passive_deletes=True
    )

    created_at: Mapped[datetime] = mapped_column(DateTime(timezone=True), default=lambda: datetime.now(timezone.utc))
