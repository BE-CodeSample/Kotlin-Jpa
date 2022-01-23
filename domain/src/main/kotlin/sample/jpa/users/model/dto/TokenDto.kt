package sample.jpa.users.model.dto

class TokenDto (
        val grantType: String,
        val accessToken: String,
        val accessTokenExpireDate: Long,
        val refreshToken: String,
        val refreshTokenExpireDate: Long,
        )
