package sample.jpa.advertisement.model.entity

import sample.jpa.CreatedUpdatedAtEntity
import sample.jpa.common.enum.Category
import sample.jpa.interest.model.entity.Interest
import javax.persistence.*

@Entity
class Advertisement(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @Lob
    @Column(name="content", nullable = false)
    var content : String,

    @Column(name="photo", nullable = true)
    var photoUrl : String,

    @Column(name="category", nullable = false)
    @Enumerated(EnumType.STRING)
    var category : Category

): CreatedUpdatedAtEntity(){}
