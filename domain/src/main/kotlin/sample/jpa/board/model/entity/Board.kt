package sample.jpa.board.model.entity

import sample.jpa.board.model.enum.BoardType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Board (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId", nullable = false)
    val id: Long? = null,

    @Column(name = "boardType", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: BoardType,

    @Column(name = "boardTitle", length = 100, nullable = false)
    val title: String,

    @Column(updatable = false, nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)