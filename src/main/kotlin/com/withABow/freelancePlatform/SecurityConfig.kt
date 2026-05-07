package com.withABow.freelancePlatform

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig (private val userDetailsService: CustomUserDetailsService){
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Keep disabled for JSON API
            .authorizeHttpRequests {
                it
                    .requestMatchers("/", "/*.html", "/static/**", "/logo.png", "/api/login").permitAll()
                    .requestMatchers("/api/users/**").hasRole("ADMIN")
                    .requestMatchers("/api/orders").hasAnyRole("ADMIN", "TUTOR")
                    .anyRequest().authenticated()
            }
            .userDetailsService(userDetailsService)
            .formLogin { it.disable() } // We use /api/login JSON endpoint
            .httpBasic { it.disable() } // Cleaner for SPA-style frontend


        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}