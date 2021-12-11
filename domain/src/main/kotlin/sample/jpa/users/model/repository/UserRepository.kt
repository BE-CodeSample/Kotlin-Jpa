package sample.jpa.users.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.jpa.users.model.entity.User

@Repository
interface UserRepository: JpaRepository<User, Long> {
}