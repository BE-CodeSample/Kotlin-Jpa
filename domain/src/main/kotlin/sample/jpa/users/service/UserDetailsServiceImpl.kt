package sample.jpa.users.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import sample.jpa.users.exception.UserNotExistException
import sample.jpa.users.model.entity.UserDetailsImpl
import sample.jpa.users.model.repository.UserRepository

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository)
    : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user = userRepository.findByUserId(userId)
                ?: throw UserNotExistException()

        return UserDetailsImpl(user)
    }
}