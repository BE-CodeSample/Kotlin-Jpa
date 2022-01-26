package sample.jpa

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
import sample.jpa.users.exception.SignUpFailedException
import sample.jpa.users.model.dto.SignUpDto
import sample.jpa.users.model.entity.User
import sample.jpa.users.model.repository.UserRepository
import sample.jpa.users.security.JwtProvider
import sample.jpa.users.service.UserService


@ExtendWith(MockitoExtension::class)
class SignUpTest {

    @Spy var jwtProvider: JwtProvider = JwtProvider("secret")
    @Spy var passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    @Mock lateinit var userRepository: UserRepository
    @InjectMocks lateinit var userService: UserService

    @Test
    @DisplayName("회원가입 테스트(성공)")
    fun signUpSuccess() {

        val signUpDto = SignUpDto(
                "test1234",
                "test@mail.com",
                "test"
        )

        given(userRepository.save(any()))
                .willReturn(User(
                        "test",
                        "test@mail.com",
                        "test1234",
                        "test"
                ))

        val tokenDto = userService.signUp(signUpDto)

        assert(tokenDto.accessToken != null)
        assert(tokenDto.refreshToken != null)
    }

    @Test
    @DisplayName("회원가입 테스트(실패) - 이메일 중복")
    fun signUpFailBecauseEmail() {
        val signUpDto = SignUpDto(
                "test1234",
                "test@mail.com",
                "test"
        )

        given(userRepository.findByEmail(any()))
                .willReturn(User(
                        "test",
                        "test@mail.com",
                        "test1234",
                        "test"
                ))

        assertThrows<SignUpFailedException> {
            userService.signUp(signUpDto)
        }
    }



}