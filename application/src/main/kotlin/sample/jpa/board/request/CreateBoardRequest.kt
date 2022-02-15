package sample.jpa.board.request

import sample.jpa.board.model.enum.BoardType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class CreateBoardRequest(
        val type: BoardType,
        @field:NotBlank(message = "제목을 입력해주세요")
        @field:Size(max = 50)
        val title: String
)
