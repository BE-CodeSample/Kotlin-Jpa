package sample.jpa.users.model.entity

import sample.jpa.interest.model.entity.UserAndInterest
import sample.jpa.post.model.entity.Post
import javax.persistence.*

@Entity
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var email: String,

    @Column(length = 50, nullable = false)
    var nickname: String,

    @Column(length = 100, nullable = false)
    var password: String,

    @OneToMany(mappedBy = "user")
    val userAndInterest: MutableList<UserAndInterest>?=null

)