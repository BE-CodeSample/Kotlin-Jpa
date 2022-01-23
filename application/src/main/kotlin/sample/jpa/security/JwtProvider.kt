package sample.jpa.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import sample.jpa.users.model.dto.TokenDto
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtProvider (
        @Value("spring.jwt.secret")
        private var secretKey: String,
        private val userDetailsService: UserDetailsService
        ) {

    private val accessTokenValidTime: Long = 60 * 60 * 1000L
    private val refreshTokenValidTime: Long = 14 * 24 * 60 * 60 * 1000L

    @PostConstruct
    fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(userId: String): TokenDto {
        val claims: Claims = Jwts.claims().setSubject(userId)
        val now = Date()

        val accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()

        val refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
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
        val claims = Jwts.parser()
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
        val claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
        return claims.subject
    }

    fun getToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
                ?.substring("Bearer ".length)
    }

}