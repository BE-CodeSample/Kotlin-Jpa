package sample.jpa.users.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class RefreshExpirationException: RuntimeException("만료된 토큰입니다.") {}