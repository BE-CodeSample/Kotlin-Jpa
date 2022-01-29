package sample.jpa.user.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sample.jpa.users.service.UserService
import sample.jpa.users.model.dto.RefreshTokenDto
import sample.jpa.users.model.dto.SignInDto
import sample.jpa.users.model.dto.SignUpDto
import sample.jpa.users.model.dto.TokenDto

@RestController
@RequestMapping("/auth")
class UserController (private val userService: UserService){

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    fun signUp(@RequestBody request: SignUpDto) : TokenDto {
        return userService.signUp(request)
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody request: SignInDto) : TokenDto {
        return userService.signIn(request)
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    fun reissue(@RequestBody request: RefreshTokenDto) : TokenDto {
        return userService.reissue(request)
    }
}