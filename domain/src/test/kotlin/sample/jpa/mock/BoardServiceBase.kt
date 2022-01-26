package sample.jpa.mock

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import sample.jpa.board.CreateBoardTest
import sample.jpa.board.model.dto.CreateBoardRequest
import sample.jpa.board.model.entity.Board
import sample.jpa.board.model.enum.BoardType
import sample.jpa.board.model.repository.BoardRepository
import sample.jpa.board.service.BoardService

@ExtendWith(MockitoExtension::class)
open class BoardServiceBase{

    @Mock
    lateinit var boardRepository: BoardRepository

    @InjectMocks
    lateinit var boardService: BoardService

    val boardId = 1L

    fun createMockBoard(boardId: Long) : Board {
        val boardTitle = "create title"
        val boardType = BoardType.BOARDTYPE_ONE

        return Board(
                id = boardId,
                type = boardType,
                title = boardTitle
        )
    }

    fun createMockBoardRequest(): CreateBoardRequest{
        val title = "create title"
        val type = BoardType.BOARDTYPE_ONE

        return CreateBoardRequest(
                type = type,
                title = title
        )
    }
}