package com.withABow.freelancePlatform

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LoginRequest(val username: String, val password: String)

@RestController
@RequestMapping("/api")
class LoginController(private val authenticationManager: AuthenticationManager) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
        session: HttpSession,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.username, request.password)
            )

            // ✅ Save to SecurityContext
            SecurityContextHolder.getContext().authentication = authentication

            // ✅ Persist to HTTP session (critical!)
            session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
            )

            ResponseEntity.ok(mapOf("message" to "Login successful", "role" to authentication.authorities.toString()))
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(401).body(mapOf("message" to "Invalid username or password"))
        }
    }
}