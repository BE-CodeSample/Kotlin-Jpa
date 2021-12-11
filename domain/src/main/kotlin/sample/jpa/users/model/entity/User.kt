package sample.jpa.users.model.entity

import javax.persistence.*

@Entity
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var email: String,

    @Column(length = 50, nullable = false)
    var nickname: String,

    @Column(length = 100, nullable = false)
    var password: String,
)