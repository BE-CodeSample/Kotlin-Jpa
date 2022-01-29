package sample.jpa.users.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(private val jwtProvider: JwtProvider)
    : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ){
        val token = jwtProvider.getToken(request)

        if (token != null && jwtProvider.validationToken(token)) {
            val userId = jwtProvider.getUserId(token)
            val authentication = jwtProvider.getAuthentication(userId)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}