import os
from pydantic_settings import BaseSettings, SettingsConfigDict

ENV = os.getenv("APP_ENV", "dev")
ENV_FILE_MAP = {"local": ".env.local", "dev": ".env", "prod": ".env"}

class Settings(BaseSettings):
    database_url: str
    tz: str = "Asia/Seoul"
    app_env: str = ENV

    model_config = SettingsConfigDict(
        env_file=None,                 # 파일은 생성자에서 주입
        env_file_encoding="utf-8",
        case_sensitive=False,
    )

# APP_ENV에 따라 다른 파일을 읽음
settings = Settings(_env_file=ENV_FILE_MAP.get(ENV, ".env"))
