package sample.jpa.users.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class RefreshTokenException: RuntimeException("accessToken을 발급할 수 없습니다.") {}