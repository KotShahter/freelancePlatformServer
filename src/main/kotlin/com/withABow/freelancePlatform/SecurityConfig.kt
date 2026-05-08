package com.withABow.freelancePlatform

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(private val userDetailsService: CustomUserDetailsService) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/", "/*.html", "/styles.css", "/app.js", "/logo.png",
                        "/**/*.css", "/**/*.js", "/**/*.png", "/static/**", "/api/login"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/users/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                    .requestMatchers("/api/orders").hasAnyRole("ADMIN", "TUTOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .userDetailsService(userDetailsService)
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            // ✅ Enable session-based auth for SPA
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) }

        return http.build()
    }

    // ✅ Expose AuthenticationManager for the login controller
    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}