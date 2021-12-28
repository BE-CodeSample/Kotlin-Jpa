package sample.jpa.comment.model.entitiy

import sample.jpa.CreatedAtEntity
import sample.jpa.CreatedUpdatedAtEntity
import sample.jpa.post.model.entity.Post
import sample.jpa.users.model.entity.User
import javax.persistence.*

@Entity
class Comment(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "commnet_id")
        val id: Long = 0,

        @Lob
        @Column(name = "content", nullable = false)
        var content: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id")
        var post: Post,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_id")
        val parent: Comment,

        @OneToMany(mappedBy = "parent", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        val children: MutableList<Comment> = ArrayList()


):CreatedAtEntity()
