package sample.jpa.board.model.entity

import sample.jpa.CreatedAtEntity
import sample.jpa.board.model.enum.BoardType
import javax.persistence.*

@Entity
data class Board (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "board_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: BoardType,

    @Column(name = "board_title", length = 100, nullable = false)
    val title: String,

) : CreatedAtEntity()