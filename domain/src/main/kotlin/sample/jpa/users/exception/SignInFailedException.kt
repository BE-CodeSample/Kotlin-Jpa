package sample.jpa.users.exception

class SignInFailedException: RuntimeException("잘못된 ID 또는 패스워드입니다.") {}