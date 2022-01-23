package sample.jpa.auth.exception

class RefreshExpirationException: RuntimeException("만료된 토큰입니다.") {}