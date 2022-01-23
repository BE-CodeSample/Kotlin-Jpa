package sample.jpa.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sample.jpa.auth.service.AuthService
import sample.jpa.users.model.dto.RefreshTokenDto
import sample.jpa.users.model.dto.SignInDto
import sample.jpa.users.model.dto.SignUpDto
import sample.jpa.users.model.dto.TokenDto

@RestController
class AuthController (private val authService: AuthService){

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    fun signUp(@RequestBody request: SignUpDto) : TokenDto {
        return authService.signUp(request)
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody reqeust: SignInDto) : TokenDto {
        return authService.signIn(reqeust)
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    fun reissue(@RequestBody request: RefreshTokenDto) : TokenDto {
        return authService.reissue(request)
    }
}