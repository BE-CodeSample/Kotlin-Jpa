package sample.jpa.board.model.entity

import sample.jpa.CreatedAtEntity
import sample.jpa.board.model.enum.BoardType
import sample.jpa.post.model.entity.Post
import javax.persistence.*

@Entity
class Board (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: BoardType,

    @Column(name = "title", length = 100, nullable = false)
    val title: String,

) : CreatedAtEntity()