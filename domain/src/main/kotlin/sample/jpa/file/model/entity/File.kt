package sample.jpa.file.model.entity

import sample.jpa.CreatedUpdatedAtEntity
import sample.jpa.users.model.entity.User
import javax.persistence.*

@Entity
class File(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "file_original_name", nullable = true)
    val fileOriginalName: String,

    //S3와 Cloudfront를 쓰게된다면 멤버변수 변경해야함
    @Column(name = "stored_directory", nullable = true)
    val storedDirectory: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    ): CreatedUpdatedAtEntity(){}