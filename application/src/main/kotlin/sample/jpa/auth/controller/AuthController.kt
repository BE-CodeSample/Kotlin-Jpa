package sample.jpa.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    fun signUp(@RequestBody request: signUpRequest) : tokenResponse {
        return signService.signUp(signUpRequest)
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody reqeust: signInReqeust) : tokenResponse {
        return signService.signIn(signInRequest)
    }
}