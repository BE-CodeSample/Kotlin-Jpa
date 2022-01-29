package sample.jpa.users.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class SignUpFailedException: RuntimeException("이미 존재하는 이메일입니다.") {}