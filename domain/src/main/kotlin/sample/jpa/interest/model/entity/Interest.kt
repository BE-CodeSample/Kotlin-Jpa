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
    val category: Category,

    @OneToMany(mappedBy = "interest")
    val userAndInterest: MutableList<UserAndInterest>?=null
): CreatedUpdatedAtEntity(){}