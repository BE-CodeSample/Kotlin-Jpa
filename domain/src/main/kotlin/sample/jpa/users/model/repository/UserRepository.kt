package sample.jpa.users.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.jpa.users.model.entity.User

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findByUserId(userId: String) : User?
    fun findByEmail(email: String) : User?
    fun findByUserIdAndRefreshToken(userId: String, token: String) : User?
}