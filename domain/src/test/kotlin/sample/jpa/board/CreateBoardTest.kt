package sample.jpa.board

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given

import org.mockito.Mockito.doReturn
import sample.jpa.board.exception.BoardTitleAlreadyExistException
import sample.jpa.board.model.enum.BoardType

import sample.jpa.mock.BoardServiceBase

class CreateBoardTest : BoardServiceBase(){

    @Test
    @DisplayName("게시판 생성 - 성공")
    fun createBoardSuccess(){
        doReturn(createMockBoard(boardId)).`when`(boardRepository).save(any())

        val request = createMockBoardRequest()
        val result = boardService.createBoard(
                type = request.type,
                title = request.title
        )

        assertThat(result.type).isEqualTo(BoardType.BOARDTYPE_ONE)
        assertThat(result.title).isEqualTo(request.title)
    }

    @Test
    @DisplayName("게시판 생성 - 실패 / 게시판 이름 이미 존재")
    fun failToCreateBoardIfDuplicateTitle(){
        given(boardRepository.existsByTitle(anyString()))
                .willReturn(true)

        val request = createMockBoardRequest()

        assertThrows<BoardTitleAlreadyExistException> {
            boardService.createBoard(
                    type = request.type,
                    title = request.title
            )
        }

    }
}