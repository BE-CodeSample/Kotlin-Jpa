package sample.jpa.interest.model.entity

import sample.jpa.CreatedUpdatedAtEntity
import sample.jpa.common.enum.Category
import javax.persistence.*

@Entity
class Interest(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long =0,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    var category: Category,

    @OneToMany(mappedBy = "interest")
    var userInterest: MutableList<UserInterest>?=null
): CreatedUpdatedAtEntity(){}