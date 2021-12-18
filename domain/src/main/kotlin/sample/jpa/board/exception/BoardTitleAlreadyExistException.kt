package sample.jpa.board.exception

class BoardTitleAlreadyExistException : RuntimeException("이미 존재하는 게시판 이름입니다.")