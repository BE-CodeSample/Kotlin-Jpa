package sample.jpa.auth.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import sample.jpa.users.model.repository.UserRepository

@Service
class AuthService (
        private val userRepository: UserRepository,
        private val jwtTokenProvider: JwtTokenProvider
        ){



}