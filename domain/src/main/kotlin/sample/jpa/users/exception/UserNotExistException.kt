package sample.jpa.users.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotExistException: RuntimeException("존재하지 않는 사용자입니다.") {}