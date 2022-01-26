package sample.jpa.board.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sample.jpa.board.exception.BoardTitleAlreadyExistException
import sample.jpa.board.model.dto.BoardResponse
import sample.jpa.board.model.entity.Board
import sample.jpa.board.model.enum.BoardType
import sample.jpa.board.model.repository.BoardRepository


@Service
class BoardService( private val boardRepository: BoardRepository ) {

    fun createBoard(
            type : BoardType, title : String
    ): BoardResponse{

        if (boardRepository.existsByTitle(title)) {
            throw BoardTitleAlreadyExistException()
        }

        val board = Board(
                id = null, title = title, type = type
        )

        val saveBoard = boardRepository.save(board)
        return BoardResponse(saveBoard)
    }
}