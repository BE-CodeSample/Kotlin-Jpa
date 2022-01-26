package sample.jpa.board.request

import sample.jpa.board.model.enum.BoardType

class CreateBoardRequest(
        val type :BoardType,

        val title : String
)
