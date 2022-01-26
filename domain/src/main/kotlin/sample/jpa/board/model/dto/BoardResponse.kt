package sample.jpa.board.model.dto

import sample.jpa.board.model.entity.Board
import sample.jpa.board.model.enum.BoardType

class BoardResponse(
        val boardId: Long?,
        val type: BoardType,
        val title: String
) {
    constructor(board : Board): this(
            boardId = board.id,
            type = board.type,
            title = board.title
    )

}