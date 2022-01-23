package sample.jpa.users.exception

class SignUpFailedException: RuntimeException("이미 존재하는 이메일입니다.") {}