package sample.jpa.users.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sample.jpa.users.exception.RefreshExpirationException
import sample.jpa.users.exception.RefreshTokenException
import sample.jpa.users.exception.SignInFailedException
import sample.jpa.users.exception.SignUpFailedException
import sample.jpa.users.exception.UserNotExistException
import sample.jpa.users.model.dto.RefreshTokenDto
import sample.jpa.users.model.dto.SignInDto
import sample.jpa.users.model.dto.SignUpDto
import sample.jpa.users.model.dto.TokenDto
import sample.jpa.users.model.entity.User
import sample.jpa.users.model.repository.UserRepository
import sample.jpa.users.security.JwtProvider
import javax.transaction.Transactional

@Service
@Transactional
open class UserService (
        private val userRepository: UserRepository,
        private val jwtProvider: JwtProvider,
        private val passwordEncoder: PasswordEncoder
        ){

        open fun signUp(dto: SignUpDto) : TokenDto {
                userRepository.findByEmail(dto.email)
                        ?. let { throw SignUpFailedException() }
                val userId = dto.email.split("@")[0]
                val token = jwtProvider.createToken(userId)
                userRepository.save(
                        User(
                                userId = userId,
                                email = dto.email,
                                password = passwordEncoder.encode(dto.password),
                                nickName = dto.nickName,
                                userInterest = null,
                                refreshToken = token.refreshToken
                        )
                )
                return token
        }

        open fun signIn(dto: SignInDto) : TokenDto {
                val user = userRepository.findByEmail(dto.email)
                        ?: throw UserNotExistException()
                if(!passwordEncoder.matches(dto.password, user.password))
                        throw SignInFailedException()

                val token = jwtProvider.createToken(user.userId)
                user.updateRefreshToken(token.refreshToken)

                return token
        }

        open fun reissue(dto: RefreshTokenDto) : TokenDto {
                if(!jwtProvider.validationToken(dto.refreshToken))
                        throw RefreshExpirationException()

                val userId = jwtProvider.getUserId(dto.refreshToken)
                val user = userRepository.findByUserIdAndRefreshToken(userId, dto.refreshToken)
                        ?: throw RefreshTokenException()

                val token = jwtProvider.createToken(user.userId)
                user.updateRefreshToken(token.refreshToken)
                return token
        }

}