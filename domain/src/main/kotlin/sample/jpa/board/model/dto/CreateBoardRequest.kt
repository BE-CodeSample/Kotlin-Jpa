package sample.jpa.board.model.dto

import sample.jpa.board.model.enum.BoardType

class CreateBoardRequest(
        val type : BoardType,

        val title : String
)
