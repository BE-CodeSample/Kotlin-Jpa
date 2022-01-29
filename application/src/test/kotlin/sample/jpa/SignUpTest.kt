package sample.jpa

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import sample.jpa.users.model.dto.SignUpDto
import sample.jpa.users.model.entity.User
import sample.jpa.users.model.repository.UserRepository

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@Rollback
class SignUpTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("회원가입 테스트(성공)")
    fun signUpSuccess() {

        val testDto = SignUpDto(
                "test1234",
                "test@mail.com",
                "test"
        )

        val test = mockMvc.post("/auth/sign-up") {
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
    @DisplayName("회원가입 테스트(실패) - 이메일 중복")
    fun signUpFailBecauseEmail() {

        userRepository.save(User(
                "test", "test@mail.com",
                "test1234", "test"
        ))

        val testDto = SignUpDto(
                "test1234",
                "test@mail.com",
                "test"
        )

        val test = mockMvc.post("/auth/sign-up") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testDto)
        }

        test.andExpect {
            status { isBadRequest() }
        }
        test.andDo { print() }
    }



}