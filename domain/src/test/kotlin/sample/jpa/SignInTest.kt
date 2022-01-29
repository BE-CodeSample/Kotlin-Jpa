package sample.jpa

import io.jsonwebtoken.Jwt
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import sample.jpa.users.exception.SignInFailedException
import sample.jpa.users.exception.UserNotExistException
import sample.jpa.users.model.dto.SignInDto
import sample.jpa.users.model.entity.User
import sample.jpa.users.model.repository.UserRepository
import sample.jpa.users.security.JwtProvider
import sample.jpa.users.service.UserService

@ExtendWith(MockitoExtension::class)
class SignInTest {

    @Spy var jwtProvider: JwtProvider = JwtProvider()
    @Mock var passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    @Mock lateinit var userRepository: UserRepository
    @InjectMocks lateinit var userService: UserService

    @Test
    @DisplayName("로그인 테스트(성공)")
    fun signInSuccess() {

        val signInDto = SignInDto("test@mail.com", "test1234")

        given(userRepository.findByEmail(any()))
                .willReturn(User(
                        "test",
                        "test@mail.com",
                        "test1234",
                        "test"
                ))

        given(passwordEncoder.matches(any(), any()))
                .willReturn(true)

        val tokenDto = userService.signIn(signInDto)
        assert(tokenDto.accessToken != null)
        assert(tokenDto.refreshToken != null)
    }

    @Test
    @DisplayName("로그인 테스트(실패) - 존재하지 않는 이메일")
    fun signInFailBecauseUserNotFound() {

        val signInDto = SignInDto("test@mail.com", "test1234")

        given(userRepository.findByEmail(any()))
                .willReturn(null)

        assertThrows<UserNotExistException> {
            userService.signIn(signInDto)
        }
    }

    @Test
    @DisplayName("로그인 테스트(실패) - 잘못된 비밀번호")
    fun signInFailBecausePasswordIsWrong() {

        val signInDto = SignInDto("test@mail.com", "test1234")

        given(userRepository.findByEmail(any()))
                .willReturn(User(
                        "test",
                        "test@mail.com",
                        "test1234",
                        "test"
                ))

        given(passwordEncoder.matches(any(), any()))
                .willReturn(false)

        assertThrows<SignInFailedException> {
            userService.signIn(signInDto)
        }
    }


}