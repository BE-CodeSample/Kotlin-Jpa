package sample.jpa.users.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import sample.jpa.interest.model.entity.UserInterest
import javax.persistence.*

@Entity
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 50, nullable = false)
    var userId: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 100, nullable = false)
    var password: String,

    @Column(length = 50, nullable = false)
    var nickName: String,

    @OneToMany(mappedBy = "user")
    var userInterest: MutableList<UserInterest>? = mutableListOf<UserInterest>(),

    @Column(nullable = false)
    var refreshToken: String
) {
    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}