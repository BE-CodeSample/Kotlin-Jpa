package sample.jpa.post.model.entity

import sample.jpa.CreatedUpdatedAtEntity
import sample.jpa.board.model.entity.Board
import sample.jpa.users.model.entity.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Lob
    @Column(nullable = true)
    var content: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Board
): CreatedUpdatedAtEntity(){}