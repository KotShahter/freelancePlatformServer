package com.withABow.freelancePlatform.services

import com.withABow.freelancePlatform.Repos.UserRepository
import com.withABow.freelancePlatform.entities.Role
import com.withABow.freelancePlatform.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
            private val userRepository : UserRepository,
            private val passwordEncoder : PasswordEncoder
)

{
    fun createUser(name: String = "noname"): User {
        val user = User(
            username = name,
            role = Role.USER,
            password = passwordEncoder.encode("123")
        )

        return userRepository.save(user)
    }

    fun getUsers() : List<User> = userRepository.findAll()


    fun getUserById(id: Int): User? =
        userRepository.findByIdOrNull(id)
}