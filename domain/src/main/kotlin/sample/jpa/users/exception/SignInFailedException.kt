package sample.jpa.users.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class SignInFailedException: RuntimeException("잘못된 ID 또는 패스워드입니다.") {}