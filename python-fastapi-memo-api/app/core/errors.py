from typing import Any, Optional

class AppError(Exception):
    status_code: int = 400
    code: str = "APP-400"
    title: str = "Bad Request"

    def __init__(self, detail: str = "", *, extra: Optional[dict[str, Any]] = None):
        self.detail = detail
        self.extra = extra or {}
    
class NotFoundError(AppError):
    status_code = 404
    code: str = "APP-404"
    title: str = "Not Found"

class ConflictError(AppError):
    status_code = 409
    code: str = "APP-409"
    title: str = "Conflict"

class UnauthorizedError(AppError):
    status_code = 401
    code = "APP-401"
    title = "Unauthorized"

class ForbiddenError(AppError):
    status_code = 403
    code = "APP-403"
    title = "Forbidden"