package sample.jpa.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sample.jpa.auth.exception.RefreshTokenException
import sample.jpa.auth.exception.RefreshExpirationException
import sample.jpa.security.JwtProvider
import sample.jpa.users.exception.SignInFailedException
import sample.jpa.users.exception.SignUpFailedException
import sample.jpa.users.model.dto.RefreshTokenDto
import sample.jpa.users.model.dto.SignInDto
import sample.jpa.users.model.dto.SignUpDto
import sample.jpa.users.model.dto.TokenDto
import sample.jpa.users.model.entity.User
import sample.jpa.users.model.repository.UserRepository
import javax.transaction.Transactional

@Service
class AuthService (
        private val userRepository: UserRepository,
        private val jwtProvider: JwtProvider,
        private val passwordEncoder: PasswordEncoder
        ){

        @Transactional
        fun signUp(dto: SignUpDto) : TokenDto {
                userRepository.findByEmail(dto.email)
                        ?. let { throw SignUpFailedException() }
                val userId = dto.email.split("@")[0]
                val token = jwtProvider.createToken(userId)
                userRepository.save(
                        User(
                                userId = userId,
                                email = dto.email,
                                password = dto.password,
                                nickName = dto.nickName,
                                userInterest = null,
                                refreshToken = token.refreshToken
                        )
                )
                return token
        }

        @Transactional
        fun signIn(dto: SignInDto) : TokenDto {
                val user = userRepository.findByEmail(dto.email)
                        ?: throw SignInFailedException()
                if(!passwordEncoder.matches(dto.password, user.password))
                        throw SignInFailedException()

                val token = jwtProvider.createToken(user.userId)
                user.updateRefreshToken(token.refreshToken)

                return token
        }

        @Transactional
        fun reissue(dto: RefreshTokenDto) : TokenDto {
                if(!jwtProvider.validationToken(dto.refreshToken))
                        throw RefreshExpirationException()

                val userId = jwtProvider.getUserId(dto.refreshToken)
                val user = userRepository.findByUserIdAndRefreshToken(userId, dto.refreshToken)
                        ?: throw RefreshTokenException()

                if(user.refreshToken != dto.refreshToken)
                        throw RefreshTokenException()

                val token = jwtProvider.createToken(userId)
                user.updateRefreshToken(token.refreshToken)
                return token
        }



}