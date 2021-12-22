package sample.jpa.post.exception

class PostNotExistException : RuntimeException("존재하지 않는 게시글입니다."){}