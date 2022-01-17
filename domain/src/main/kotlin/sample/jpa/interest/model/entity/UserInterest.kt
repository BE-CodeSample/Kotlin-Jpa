package sample.jpa.interest.model.entity

import sample.jpa.CreatedUpdatedAtEntity
import sample.jpa.users.model.entity.User
import javax.persistence.*

@Entity
class UserInterest (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user : User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id", nullable= false)
    val interest : Interest

):CreatedUpdatedAtEntity(){}