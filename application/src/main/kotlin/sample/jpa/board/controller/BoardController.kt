package sample.jpa.board.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sample.jpa.board.model.dto.BoardResponse
import sample.jpa.board.request.CreateBoardRequest
import sample.jpa.board.service.BoardService

@RestController
@RequestMapping("/board")
class BoardController(private val boardService: BoardService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(
            @RequestBody req: CreateBoardRequest
    ):BoardResponse{
        return boardService.createBoard(req.type, req.title)
    }

}