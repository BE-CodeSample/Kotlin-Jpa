package sample.jpa.users.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import sample.jpa.users.model.dto.TokenDto
import sample.jpa.users.service.UserDetailsServiceImpl
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
open class JwtProvider (
        @Value("spring.jwt.secret")
        private var secretKey: String,
        private val userDetailsService: UserDetailsService
        ) {
    private val accessTokenValidTime: Long = 60 * 60 * 1000L
    private val refreshTokenValidTime: Long = 14 * 24 * 60 * 60 * 1000L

    @PostConstruct
    private fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(userId: String): TokenDto {
        val accessClaims: Claims = Jwts.claims()
        accessClaims["userId"] = userId
        accessClaims["type"] = "access"

        val refreshClaims: Claims = Jwts.claims()
        refreshClaims["userId"] = userId
        refreshClaims["type"] = "refresh"

        val now = Date()

        val accessToken = Jwts.builder()
                .setClaims(accessClaims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()

        val refreshToken = Jwts.builder()
                .setClaims(refreshClaims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()

        return TokenDto(
                "bearer",
                accessToken,
                accessTokenValidTime,
                refreshToken,
                refreshTokenValidTime
        )
    }

    fun validationToken(token: String): Boolean {
        val claims: Claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
        return claims.expiration.after(Date())
    }

    fun getAuthentication(userId: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(userId)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    fun getUserId(token: String): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body

        return claims["userId"] as String
    }

    fun getToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
                ?.substring("bearer ".length)
    }
    constructor() : this(
            secretKey = "secret",
            userDetailsService = UserDetailsServiceImpl(null)
    )
}