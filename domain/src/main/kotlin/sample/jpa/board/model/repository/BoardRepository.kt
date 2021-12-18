package sample.jpa.board.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.jpa.board.model.entity.Board

@Repository
interface BoardRepository: JpaRepository<Board, Long> {
    fun existsByBoardTitle(title: String): Boolean
}