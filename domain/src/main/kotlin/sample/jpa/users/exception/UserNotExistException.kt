package sample.jpa.users.exception

class UserNotExistException: RuntimeException("존재하지 않는 사용자입니다.") {}