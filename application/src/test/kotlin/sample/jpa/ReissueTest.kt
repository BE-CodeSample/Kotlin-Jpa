package sample.jpa

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import sample.jpa.users.service.UserService
import sample.jpa.users.model.dto.RefreshTokenDto
import sample.jpa.users.model.dto.SignUpDto
import sample.jpa.users.model.repository.UserRepository
import sample.jpa.users.security.JwtProvider

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ReissueTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var jwtProvider: JwtProvider

    var refreshToken = ""

    @BeforeEach
    fun createRefresh() {
        val token = userService.signUp(
                SignUpDto( "test1234",
                        "test@mail.com",
                        "test"
        ))

        refreshToken = token.refreshToken
    }

    @Test
    @DisplayName("토큰 재발급 테스트(성공)")
    fun reissueSuccess() {

        val testDto = RefreshTokenDto(refreshToken)

        val test = mockMvc.post("/auth/reissue") {
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
    @DisplayName("토큰 재발급 테스트(실패) - 유효하지 않은 사용자")
    fun reissueFailBecauseNotExistUser() {

        //Given
        val token = jwtProvider.createToken("test2")
        val testDto = RefreshTokenDto(token.refreshToken)
        //When
        val test = mockMvc.post("/auth/reissue") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testDto)
        }
        //Then
        test.andExpect {
            status { isUnauthorized() }
        }
    }
}