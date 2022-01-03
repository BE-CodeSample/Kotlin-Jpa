package sample.jpa.report.model.entity

import sample.jpa.CreatedAtEntity
import sample.jpa.post.model.entity.Post
import sample.jpa.report.model.enum.ReportState
import sample.jpa.report.model.enum.ReportType
import sample.jpa.users.model.entity.User
import javax.persistence.*

@Entity
class Report (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 0,

        @Column(name = "title", length = 100, nullable = false)
        var title: String,

        @Lob
        @Column(name = "content", nullable = true)
        var content: String?,

        @Enumerated(EnumType.STRING)
        @Column(name = "state", nullable = false)
        var state: ReportState,

        @Enumerated(EnumType.STRING)
        @Column(name = "type", nullable = false)
        var type: ReportType,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", nullable = false)
        val post: Post

):CreatedAtEntity()