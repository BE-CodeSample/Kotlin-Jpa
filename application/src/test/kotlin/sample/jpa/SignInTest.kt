package sample.jpa

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import sample.jpa.users.model.dto.SignInDto
import sample.jpa.users.model.entity.User
import sample.jpa.users.model.repository.UserRepository

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class SignInTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun saveUser() {
        val password = passwordEncoder.encode("test1234")
        userRepository.save(User(
                "test", "test@mail.com",
                password, "test"
        ))
    }

    @Test
    @DisplayName("로그인 테스트(성공)")
    fun signInSuccess() {
        val testDto = SignInDto(
                "test@mail.com",
                "test1234"
        )

        val test = mockMvc.post("/auth/sign-in") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testDto)
        }

        test.andExpect {
            status { isOk() }
            jsonPath("grantType") { value("bearer") }
            jsonPath("accessToken") { exists() }
            jsonPath("accessTokenExpireDate") { exists() }
            jsonPath("refreshToken") { exists() }
            jsonPath("refreshTokenExpireDate") { exists() }
        }
        test.andDo { print() }
    }

    @Test
    @DisplayName("로그인 테스트(실패) - 존재하지 않는 사용자")
    fun signInFailBecauseUserNotFound() {

        val testDto = SignInDto(
                "test2@mail.com",
                "test1234"
        )

        val test = mockMvc.post("/auth/sign-in") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testDto)
        }

        test.andExpect {
            status { isNotFound() }
        }
        test.andDo { print() }
    }

    @Test
    @DisplayName("로그인 테스트(실패) - 잘못된 비밀번호")
    fun signInFailBecausePasswordIsWrong() {

        val testDto = SignInDto(
                "test@mail.com",
                "test1234567890"
        )

        val test = mockMvc.post("/auth/sign-in") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testDto)
        }

        test.andExpect {
            status { isBadRequest() }
        }
        test.andDo { print() }
    }
}