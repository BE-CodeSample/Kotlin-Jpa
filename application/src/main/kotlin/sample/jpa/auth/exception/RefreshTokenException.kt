package sample.jpa.auth.exception

class RefreshTokenException: RuntimeException("accessToken을 발급할 수 없습니다.") {}